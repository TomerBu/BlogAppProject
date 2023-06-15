package edu.tomerbu.blogproject.controller;

import edu.tomerbu.blogproject.dto.SignUpRequestDto;
import edu.tomerbu.blogproject.dto.SignUpResponseDto;
import edu.tomerbu.blogproject.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserDetailsServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto dto) {
        return new ResponseEntity<>(authService.signUp(dto), HttpStatus.CREATED);
    }
}
