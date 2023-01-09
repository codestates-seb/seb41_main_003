package com.mainproject.server.profile.controller;

import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.profile.dto.ProfileDto;
import com.mainproject.server.profile.dto.ProfileResponseDto;
import com.mainproject.server.profile.dto.ProfileSimpleResponseDto;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final StubData stubData;

    @GetMapping("/{userId}")
    public ResponseEntity getProfiles(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "profileId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ProfileSimpleResponseDto simple = stubData.createProfileSimpleResponse();
        List<ProfileSimpleResponseDto> simpleList = List.of(simple, simple);
        Page<ProfileSimpleResponseDto> page = new PageImpl<>(simpleList, pageable, 10L);
        PageResponseDto response = PageResponseDto.of(simpleList, page);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/details/{profileId}")
    private ResponseEntity getProfile(
            @PathVariable Long profileId,
            @PageableDefault(page = 0, size = 10, sort = "reviewId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ProfileResponseDto profile = stubData.createProfileResponse();
        PageResponseDto response = PageResponseDto.of(
                profile.getReviews().getContent(),
                profile.getReviews());
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @PostMapping("/{userId}")
    private ResponseEntity postProfile(
            @PathVariable Long userId,
            @RequestBody @Validated ProfileDto profileDto
    ) {
        ProfileSimpleResponseDto response = stubData.createProfileSimpleResponse();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/details/{profileId}")
    private ResponseEntity patchProfile(
            @PathVariable Long profileId,
            @RequestBody ProfileDto profileDto
    ) {
        ProfileResponseDto profile = stubData.createProfileResponse();
        PageResponseDto response = PageResponseDto.of(
                profile.getReviews().getContent(),
                profile.getReviews());
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/details/{profileId}")
    private ResponseEntity deleteProfile(
            @PathVariable Long profileId
    ) {
        return ResponseEntity.noContent().build();
    }
}
