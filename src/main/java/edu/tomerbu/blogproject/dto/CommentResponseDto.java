package edu.tomerbu.blogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CommentResponseDto {
    private long id;
    private UserResponseDto user;
    private String comment;
}
