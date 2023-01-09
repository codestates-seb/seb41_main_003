package com.mainproject.server.review.controller;

import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.utils.StubData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private StubData stubData;

    @GetMapping("/{tutoringId}")
    public ResponseEntity getReview(@PathVariable Long tutoringId) {
        return new ResponseEntity(ResponseDto.of(stubData.createReviewResponse()), HttpStatus.OK);
    }

    @PostMapping("/{tutoringId}")
    public ResponseEntity postReview(@PathVariable Long tutoringId) {
        return new ResponseEntity(ResponseDto.of(stubData.createReviewResponse()), HttpStatus.CREATED);
    }

    @PatchMapping("/{tutoringId}")
    public ResponseEntity patchReview(@PathVariable Long tutoringId) {
        return new ResponseEntity(ResponseDto.of(stubData.createReviewResponse()), HttpStatus.OK);
    }
}
