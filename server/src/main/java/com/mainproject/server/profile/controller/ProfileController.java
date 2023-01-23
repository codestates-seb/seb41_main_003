package com.mainproject.server.profile.controller;

import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.profile.dto.ProfileDto;
import com.mainproject.server.profile.dto.ProfilePageDto;
import com.mainproject.server.profile.dto.ProfileResponseDto;
import com.mainproject.server.profile.dto.WantedDto;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.mapper.ProfileMapper;
import com.mainproject.server.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
@Validated
public class ProfileController {

    private final ProfileService profileService;

    private final ProfileMapper profileMapper;

    @GetMapping("/{userId}")
    public ResponseEntity getProfiles(
            @PathVariable @Positive Long userId
    ) {
        List<Profile> profiles = profileService.getProfiles(userId);
        return new ResponseEntity<>(
                ResponseDto.of(profileMapper.entityListToSimpleResponseDtoList(profiles)),
                HttpStatus.OK
        );
    }

    @GetMapping("/details/{profileId}")
    public ResponseEntity getProfile(
            @PathVariable @Positive Long profileId,
            @PageableDefault(page = 0, size = 5, sort = "reviewId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ProfilePageDto profile = profileService.getProfile(profileId,pageable);
        ProfileResponseDto responseDto = ProfileResponseDto.of(profile);
        return new ResponseEntity<>(
                PageResponseDto.of(
                        responseDto,
                        profile.getReviews()),
                HttpStatus.OK
        );
    }

    @PostMapping("/{userId}")
    public ResponseEntity postProfile(
            @PathVariable @Positive Long userId,
            @RequestBody @Valid ProfileDto profileDto,
            @PageableDefault(page = 0, size = 5, sort = "reviewId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ProfilePageDto saveProfile = profileService.createProfile(
                userId,
                profileMapper.dtoToEntity(profileDto),
                profileDto.getSubjects(),
                pageable);
        ProfileResponseDto responseDto = ProfileResponseDto.of(saveProfile);
        return new ResponseEntity<>(
                PageResponseDto.of(
                        responseDto,
                        saveProfile.getReviews()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/details/{profileId}")
    public ResponseEntity patchProfile(
            @PathVariable @Positive Long profileId,
            @RequestBody @Valid ProfileDto profileDto,
            @PageableDefault(page = 0, size = 5, sort = "reviewId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ProfilePageDto profile = profileService.updateProfile(
                profileId,
                profileMapper.dtoToEntity(profileDto),
                profileDto.getSubjects(),
                pageable
        );
        ProfileResponseDto responseDto = ProfileResponseDto.of(profile);
        PageResponseDto response = PageResponseDto.of(
                responseDto,
                profile.getReviews());
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @PatchMapping("/status/{profileId}")
    public ResponseEntity patchProfileStatus(
            @PathVariable @Positive Long profileId,
            @RequestBody @Valid WantedDto wantedDto,
            @PageableDefault(page = 0, size = 5, sort = "reviewId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ProfilePageDto profile = profileService.updateWantedStatus(
                profileId,
                wantedDto,
                pageable
        );
        ProfileResponseDto responseDto = ProfileResponseDto.of(profile);
        PageResponseDto response = PageResponseDto.of(
                responseDto,
                profile.getReviews());
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/details/{profileId}")
    public ResponseEntity deleteProfile(
            @PathVariable @Positive Long profileId
    ) {
        profileService.deleteProfile(profileId);
        return ResponseEntity.noContent().build();
    }


}
