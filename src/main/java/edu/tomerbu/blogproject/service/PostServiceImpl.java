package edu.tomerbu.blogproject.service;

import edu.tomerbu.blogproject.dto.PostPageResponseDto;
import edu.tomerbu.blogproject.dto.PostRequestDto;
import edu.tomerbu.blogproject.dto.PostResponseDto;
import edu.tomerbu.blogproject.dto.PostWithCommentsDto;
import edu.tomerbu.blogproject.entity.Post;
import edu.tomerbu.blogproject.error.ResourceNotFoundException;
import edu.tomerbu.blogproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        //convert requestDTO To Entity, (title, description, content, null)
        Post entity = modelMapper.map(postRequestDto, Post.class);

        //save the entity -> now post has an id!
        var saved = postRepository.save(entity);

        //convert saved entity to ResponseDto
        return modelMapper.map(saved, PostResponseDto.class);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(p -> modelMapper.map(p, PostResponseDto.class))
                .toList();
    }


    @Override
    public PostResponseDto getPostById(long id) {
        return modelMapper.map(getPostEntity(id), PostResponseDto.class);
    }

    //dont copy paste -> reuse
    private Post getPostEntity(long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", id)
        );
    }

    @Override
    public PostResponseDto updatePostById(PostRequestDto dto, long id) {
        Post postFromDb = getPostEntity(id); //assert that id exists

        //update => copy new props from request dto
        postFromDb.setContent(dto.getContent());
        postFromDb.setTitle(dto.getTitle());
        postFromDb.setDescription(dto.getDescription());

        //save
        var saved = postRepository.save(postFromDb);

        //return response dto
        return modelMapper.map(saved, PostResponseDto.class);
    }

    @Override
    public PostResponseDto deletePostById(long id) {
        //if the post does not exist->404 resource not found
        Post post = getPostEntity(id);
        postRepository.deleteById(id);

        return modelMapper.map(post, PostResponseDto.class);
    }

    public boolean deletePostByIdWithBoolean(long id) {
        var exists = postRepository.existsById(id);
        postRepository.deleteById(id);

        return exists;
    }


    @Override
    public PostPageResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);

        Page<Post> page = postRepository.findAll(pageable);

        return PostPageResponseDto.builder()
                .results(page.stream().map(post -> modelMapper.map(post, PostWithCommentsDto.class)).toList())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .pageNo(page.getNumber())
                .totalPosts(page.getTotalElements())
                .isLast(page.isLast())
                .isFirst(page.isFirst())
                .build();
    }
}
