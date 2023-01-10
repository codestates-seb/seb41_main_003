package com.mainproject.server.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mainproject.server.webhook.WebHookDto;
import com.mainproject.server.webhook.WebHookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final WebHookService webHookService;

    @GetMapping
    public ResponseEntity helloWorld() {
        return ResponseEntity.ok().body(
                Map.of("hello", "world")
        );
    }
    @PostMapping("web-hook")
    public ResponseEntity postWebHook(
            @RequestBody WebHookDto request
    ) throws JsonProcessingException {
        webHookService.callEvent(request);

        return ResponseEntity.ok().build();
    }
}