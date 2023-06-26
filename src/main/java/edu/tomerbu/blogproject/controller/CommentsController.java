package edu.tomerbu.blogproject.controller;

import edu.tomerbu.blogproject.dto.CommentRequestDto;
import edu.tomerbu.blogproject.dto.CommentResponseDto;
import edu.tomerbu.blogproject.dto.CommentUpdateRequestDto;
import edu.tomerbu.blogproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentsController {
    private final CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable(name = "id") long postId,
            @RequestBody CommentRequestDto dto,
            UriComponentsBuilder uriBuilder,
            Authentication authentication
    ) {
        var saved = commentService.createComment(postId, dto, authentication);
        var uri =
                uriBuilder
                        .path("/api/v1/posts/{post_id}/{comment_id}")
                        .buildAndExpand(postId, saved.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    //http://localhost:8080/api/v1/posts/4/comments
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> findCommentsByPostId(@PathVariable("id") long postId) {
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
    }

    //PUT http://localhost:8080/api/v1/comments/1
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateCommentById(
            @PathVariable("id") long commentId,
            @RequestBody CommentUpdateRequestDto dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                commentService.updateComment(commentId, dto, authentication
                ));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> deleteCommentById(
            @PathVariable long id, Authentication authentication) {
        return ResponseEntity.ok(
                commentService.deleteCommentById(id, authentication
                ));
    }
}
