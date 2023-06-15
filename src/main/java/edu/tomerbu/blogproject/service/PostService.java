package edu.tomerbu.blogproject.service;

import edu.tomerbu.blogproject.dto.PostPageResponseDto;
import edu.tomerbu.blogproject.dto.PostRequestDto;
import edu.tomerbu.blogproject.dto.PostResponseDto;

import java.util.List;

//SOLID Principles: prefer abstraction to implementations
public interface PostService {
    PostResponseDto createPost(PostRequestDto postRequestDto);

    List<PostResponseDto> getAllPosts();

    //get post by id:
    PostResponseDto getPostById(long id);

    PostResponseDto updatePostById(PostRequestDto dto, long id);

    PostResponseDto deletePostById(long id);

    PostPageResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
