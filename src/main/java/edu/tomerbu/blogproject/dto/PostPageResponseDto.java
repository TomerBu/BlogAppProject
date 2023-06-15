package edu.tomerbu.blogproject.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPageResponseDto {
    private List<PostWithCommentsDto> results;
    private int totalPages;
    private long totalPosts;
    private boolean isLast;
    private boolean isFirst;
    /**
     * current page
     */
    private int pageNo;
    /**
     * current page size
     */
    private int pageSize;
}
