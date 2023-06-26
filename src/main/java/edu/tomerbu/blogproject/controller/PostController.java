package edu.tomerbu.blogproject.controller;

import edu.tomerbu.blogproject.dto.PostPageResponseDto;
import edu.tomerbu.blogproject.dto.PostRequestDto;
import edu.tomerbu.blogproject.dto.PostResponseDto;
import edu.tomerbu.blogproject.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    //props:
    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody @Valid PostRequestDto dto, UriComponentsBuilder uriBuilder) {

        var saved = postService.createPost(dto);

        var uri = uriBuilder.path("/api/v1/posts/{id}").buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    //api/v1/posts/3
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(
            @Valid @NotNull @PathVariable long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //api/v1/posts/3
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponseDto> updatePostById(
            @Valid @NotNull @PathVariable long id,
            @Valid @RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.updatePostById(dto, id));
    }

    //DELETE api/v1/posts/3
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponseDto> deletePostById(
            @Valid @NotNull @PathVariable long id) {
        return ResponseEntity.ok(postService.deletePostById(id));
    }

    //GET api/v1/posts/page
    //GET api/v1/posts/page?pageSize=25&pageNo=2&sortBy=title&sortDir=desc
    @GetMapping("/page")
    public ResponseEntity<PostPageResponseDto> getPostsPage(
        @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
        @RequestParam(value = "pageSize",required = false, defaultValue = "10") int pageSize,
        @RequestParam(value = "sortBy",required = false, defaultValue = "id") String sortBy,
        @RequestParam(value = "sortDir",required = false, defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }
}
