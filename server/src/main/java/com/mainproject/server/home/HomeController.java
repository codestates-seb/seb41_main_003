package com.mainproject.server.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.webhook.WebHookDto;
import com.mainproject.server.webhook.WebHookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final WebHookService webHookService;

    @GetMapping
    public ResponseEntity  helloWorld() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/docs/index.html"));
        return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("web-hook")
    public ResponseEntity postWebHook() {
        throw new ServiceLogicException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}