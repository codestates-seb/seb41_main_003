package com.mainproject.server.tutoring.controller;

import com.mainproject.server.dateNotice.dto.DateNoticePatchDto;
import com.mainproject.server.dateNotice.dto.DateNoticePostDto;
import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.tutoring.dto.*;
import com.mainproject.server.utils.StubData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tutoring")
public class TutoringController {

    private final StubData stubData;

    @PostMapping("/{profileId}")
    public ResponseEntity postTutoring(
            @PathVariable("profileId") Long profileId,
            @RequestBody TutoringPostDto tutoringPostDto
            ) {
        return new ResponseEntity(
                ResponseDto.of(stubData.createTutoringSimpleResponse()),
                HttpStatus.CREATED);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity getAllTutoring(
            @PathVariable("profileId") Long profileId,
            @PageableDefault(page = 0, size = 10, sort = "profileId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        TutoringSimpleResponseDto tutoringSimpleResponse = stubData.createTutoringSimpleResponse();
        List<TutoringSimpleResponseDto> tutoringList = List.of(tutoringSimpleResponse, tutoringSimpleResponse, tutoringSimpleResponse);
        Page<TutoringSimpleResponseDto> page = new PageImpl<>(tutoringList, pageable, 10L);
        PageResponseDto response = PageResponseDto.of(tutoringList, page);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/details/{tutoringId}")
    public ResponseEntity postTutoringMatch(
            @PathVariable("tutoringId") Long tutoringId
    ) {
        return new ResponseEntity(
                ResponseDto.of(stubData.createTutoringSimpleResponse()),
                HttpStatus.OK);
    }


    @GetMapping("/details/{tutoringId}")
    public ResponseEntity getTutoring(
            @PathVariable("tutoringId") Long tutoringId
    ) {

        TutoringDto tutoringDto = stubData.createTutoringDto();
        TutoringResponseDto response = TutoringResponseDto.of(tutoringDto);
        return new ResponseEntity(
                ResponseDto.of(response),
                HttpStatus.OK);
    }

    @PatchMapping("/details/{tutoringId}")
    public ResponseEntity patchTutoring(
            @PathVariable("tutoringId") Long tutoringId,
            @RequestBody TutoringPatchDto tutoringPatchDto
            ) {
        TutoringDto tutoringDto = stubData.createTutoringDto();
        TutoringResponseDto response = TutoringResponseDto.of(tutoringDto);
        return new ResponseEntity(
                PageResponseDto.of(response,tutoringDto.getDateNotices()),
                HttpStatus.OK);
    }

    @DeleteMapping("/details/{tutoringId}")
    public ResponseEntity deleteTutoring(
            @PathVariable("tutoringId") Long tutoringId
    ) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/date-notice")
    public ResponseEntity postDateNotice(
            @RequestBody DateNoticePostDto dateNoticePostDto
            ) {
        return new ResponseEntity(
                ResponseDto.of(stubData.createDateNoticeResponse()),
                HttpStatus.CREATED);
    }

    @GetMapping("/date-notice/{dateNoticeId}")
    public ResponseEntity getDateNotice(
            @PathVariable("dateNoticeId") Long dateNoticeId,
            Principal principal
    ) {
        return new ResponseEntity(
                ResponseDto.of(stubData.createDateNoticeResponse()),
                HttpStatus.OK);
    }

    @PatchMapping("/date-notice/{dateNoticeId}")
    public ResponseEntity patchDateNotice(
            @PathVariable("dateNoticeId") Long dateNoticeId,
            @RequestBody DateNoticePatchDto dateNoticePatchDto
            ) {
        return new ResponseEntity(
                ResponseDto.of(stubData.createDateNoticeResponse()),
                HttpStatus.OK);
    }

    @DeleteMapping("/date-notice/{dateNoticeId}")
    public ResponseEntity deleteDateNotice(
            @PathVariable("dateNoticeId") Long dateNoticeId
    ) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
