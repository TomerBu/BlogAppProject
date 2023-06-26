package edu.tomerbu.blogproject.service;

import edu.tomerbu.blogproject.dto.CommentRequestDto;
import edu.tomerbu.blogproject.dto.CommentResponseDto;
import edu.tomerbu.blogproject.dto.CommentUpdateRequestDto;
import edu.tomerbu.blogproject.entity.Comment;
import edu.tomerbu.blogproject.error.BadRequestException;
import edu.tomerbu.blogproject.error.ResourceNotFoundException;
import edu.tomerbu.blogproject.repository.CommentRepository;
import edu.tomerbu.blogproject.repository.PostRepository;
import edu.tomerbu.blogproject.repository.RoleRepository;
import edu.tomerbu.blogproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    //props:
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Override
    public CommentResponseDto createComment(long postId, CommentRequestDto dto, Authentication authentication) {
        var post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", postId)
        );

        //אם המשתמש הצליח להגיע לפה סימן שיש כזה משתמש
        var user = userRepository.findByUsernameIgnoreCase(authentication.getName()).orElseThrow();
        Comment comment = modelMapper.map(dto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);

        var saved = commentRepository.save(comment);
        return modelMapper.map(saved, CommentResponseDto.class);
    }

    @Override
    public List<CommentResponseDto> findCommentsByPostId(long postId) {
        //assert that the post exists:
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("post", postId);
        }
        //return the comments for the post:
        return commentRepository
                .findCommentsByPostId(postId)
                .stream()
                .map(
                        c -> modelMapper.map(c, CommentResponseDto.class)
                ).toList();
    }


    @Override
    @Transactional
    public CommentResponseDto updateComment(long commentId, CommentUpdateRequestDto dto, Authentication authentication) {
        //var fromDb = fetch comment from database with commentId => orElseThrow
        var commentFromDb =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));

        userHasPermissionToEditComment(authentication, commentFromDb);

        //copy fields to update:
        //fromDb.title = dto.title (copy)
        commentFromDb.setComment(dto.getComment());

        //save fromDb again
        var saved = commentRepository.save(commentFromDb);

        //return a dto
        return modelMapper.map(saved, CommentResponseDto.class);
    }

    private void userHasPermissionToEditComment(Authentication authentication, Comment commentFromDb) {
        //get the user from the database comment:
        var user = commentFromDb.getUser();

        //get the admin role from the database:
        var adminRole = roleRepository.findByNameIgnoreCase("ROLE_ADMIN").orElseThrow();

        //check if the user role is Admin:
        var isAdmin = user.getRoles().contains(adminRole);

        //check if the user owns the comment:
        var userOwnsComment = user.getUsername().equalsIgnoreCase(authentication.getName());

        if (!isAdmin && !userOwnsComment){
            throw new BadRequestException("user", "Comment must belong the editing user");
        }
    }

    @Override
    @Transactional
    public CommentResponseDto deleteCommentById(long commentId, Authentication authentication) {
        //var saved = find the comment -> or else throw
        var saved =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));

        //validation: userHasPermissionToEditComment
        userHasPermissionToEditComment(authentication, saved);

        //delete by id
        commentRepository.deleteById(commentId);

        //return the deleted post as a response
        return modelMapper.map(saved, CommentResponseDto.class);
    }
}
