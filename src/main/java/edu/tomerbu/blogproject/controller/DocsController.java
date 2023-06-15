package edu.tomerbu.blogproject.controller;

import edu.tomerbu.blogproject.dto.PostPageResponseDto;
import edu.tomerbu.blogproject.dto.PostRequestDto;
import edu.tomerbu.blogproject.dto.PostResponseDto;
import edu.tomerbu.blogproject.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/docs")
@RequiredArgsConstructor
public class DocsController {
    @GetMapping
    ResponseEntity<Object> getDocs() {
       return ResponseEntity.ok(Map.of("message", "docs"));
    }
}
