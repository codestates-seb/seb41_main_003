package com.mainproject.server.review.controller;

import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.review.dto.ReviewPatchDto;
import com.mainproject.server.review.dto.ReviewPostDto;
import com.mainproject.server.utils.StubData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/reviews")
public class ReviewController {

    private final StubData stubData;

    @GetMapping("/{tutoringId}")
    public ResponseEntity getReview(@PathVariable("tutoringId") Long tutoringId) {
        return new ResponseEntity(ResponseDto.of(stubData.createReviewResponse()), HttpStatus.OK);
    }

    @PostMapping("/{tutoringId}")
    public ResponseEntity postReview(@PathVariable("tutoringId") Long tutoringId,
                                     @RequestBody ReviewPostDto reviewPostDto) {
        return new ResponseEntity(ResponseDto.of(stubData.createReviewResponse()), HttpStatus.CREATED);
    }

    @PatchMapping("/{tutoringId}")
    public ResponseEntity patchReview(@PathVariable("tutoringId") Long tutoringId,
                                      @RequestBody ReviewPatchDto reviewPatchDto) {
        return new ResponseEntity(ResponseDto.of(stubData.createReviewResponse()), HttpStatus.OK);
    }
}
