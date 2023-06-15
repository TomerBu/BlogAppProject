package edu.tomerbu.blogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CommentRequestDto {
    private String username;
    private String email;
    private String comment;
}
