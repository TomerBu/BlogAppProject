package edu.tomerbu.blogproject.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Hidden
@RestController
@RequestMapping("/docs")
@RequiredArgsConstructor
public class DocsController {
    @GetMapping
    ResponseEntity<Object> getDocs() {
       return ResponseEntity.ok(Map.of("message", "docs"));
    }
}
