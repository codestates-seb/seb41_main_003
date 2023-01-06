package com.mainproject.server.home;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public ResponseEntity helloWorld() {
        return ResponseEntity.ok().body(
                Map.of("hello", "world")
        );
    }
}