package edu.tomerbu.blogproject.service;

import edu.tomerbu.blogproject.dto.CommentRequestDto;
import edu.tomerbu.blogproject.dto.CommentResponseDto;
import edu.tomerbu.blogproject.dto.CommentUpdateRequestDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(long postId, CommentRequestDto dto);
    List<CommentResponseDto> findCommentsByPostId(long postId);
    CommentResponseDto updateComment(long commentId, CommentUpdateRequestDto dto);
    CommentResponseDto deleteCommentById(long commentId);
}
