package edu.tomerbu.blogproject.controller;

import edu.tomerbu.blogproject.dto.SignInRequestDto;
import edu.tomerbu.blogproject.dto.SignUpRequestDto;
import edu.tomerbu.blogproject.dto.SignUpResponseDto;
import edu.tomerbu.blogproject.security.JWTProvider;
import edu.tomerbu.blogproject.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserDetailsServiceImpl authService;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto dto) {
        return new ResponseEntity<>(authService.signUp(dto), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody @Valid SignInRequestDto dto){
        var user = authService.loadUserByUsername(dto.getUsername());

        var savedPassword = user.getPassword();
        var givenPassword = dto.getPassword();

        if (passwordEncoder.matches(givenPassword, savedPassword)) {
            //grant:
            var token = jwtProvider.generateToken(user.getUsername());

            return ResponseEntity.ok(Map.of("jwt", token));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
