package edu.tomerbu.blogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//User without a password:
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private long id;
    private String username;    
    private String email;
}