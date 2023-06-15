package edu.tomerbu.blogproject.dto;

import edu.tomerbu.blogproject.validators.UniqueTitle;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link edu.tomerbu.blogproject.entity.Post} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDto {
    @NotNull
    @Size(min = 2, max = 255)
    @UniqueTitle
    private String title;
    @NotNull
    @Size(min = 2, max = 1000)
    private String description;
    @NotNull
    @Size(min = 2)
    private String content;
}