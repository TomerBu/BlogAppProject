package edu.tomerbu.blogproject.service;

import edu.tomerbu.blogproject.dto.CommentRequestDto;
import edu.tomerbu.blogproject.dto.CommentResponseDto;
import edu.tomerbu.blogproject.dto.CommentUpdateRequestDto;
import org.springframework.security.core.Authentication;
import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(long postId, CommentRequestDto dto, Authentication authentication);
    List<CommentResponseDto> findCommentsByPostId(long postId);
    CommentResponseDto updateComment(long commentId, CommentUpdateRequestDto dto, Authentication authentication);
    CommentResponseDto deleteCommentById(long commentId, Authentication authentication);
}
