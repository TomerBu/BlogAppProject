package edu.tomerbu.blogproject.service;

import edu.tomerbu.blogproject.dto.CommentRequestDto;
import edu.tomerbu.blogproject.dto.CommentResponseDto;
import edu.tomerbu.blogproject.dto.CommentUpdateRequestDto;
import edu.tomerbu.blogproject.entity.Comment;
import edu.tomerbu.blogproject.error.ResourceNotFoundException;
import edu.tomerbu.blogproject.repository.CommentRepository;
import edu.tomerbu.blogproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    //props:
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentResponseDto createComment(long postId, CommentRequestDto dto) {
        var post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", postId)
        );

        var comment = modelMapper.map(dto, Comment.class);
        comment.setPost(post);

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
    public CommentResponseDto updateComment(long commentId, CommentUpdateRequestDto dto) {
        //var fromDb = fetch comment from database with commentId => orElseThrow
        var commentFromDb =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));

        //copy fields to update:
        //fromDb.title = dto.title (copy)
        commentFromDb.setComment(dto.getComment());

        //save fromDb again
        var saved = commentRepository.save(commentFromDb);

        //return a dto
        return modelMapper.map(saved, CommentResponseDto.class);
    }

    @Override
    public CommentResponseDto deleteCommentById(long commentId) {
        //var saved = find the comment -> or else throw
        var saved =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));
        //delete by id
        commentRepository.deleteById(commentId);

        //return the deleted post as a response
        return modelMapper.map(saved, CommentResponseDto.class);
    }
}
