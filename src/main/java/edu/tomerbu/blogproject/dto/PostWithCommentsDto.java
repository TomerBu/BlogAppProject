package edu.tomerbu.blogproject.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostWithCommentsDto {
    private Long id;
    private String title;
    private String description;
    private String content;

    private List<CommentResponseDto> comments;
}
