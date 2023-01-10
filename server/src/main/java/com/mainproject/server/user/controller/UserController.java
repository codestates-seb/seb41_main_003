package com.mainproject.server.user.controller;

import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.profile.dto.ProfileListResponseDto;
import com.mainproject.server.user.dto.UserPatchDto;
import com.mainproject.server.user.dto.UserPostDto;
import com.mainproject.server.user.dto.UserResponseDto;
import com.mainproject.server.utils.StubData;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final StubData stubData;

    @GetMapping("/{userId}")
    public ResponseEntity getUser(
            @PathVariable Long userId
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubData.createUserResponse()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postUser(
            @RequestBody @Validated UserPostDto userPostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubData.createUserResponse()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(
            @PathVariable Long userId,
            @RequestBody UserPatchDto userPatchDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubData.createUserResponse()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tutors")
    public ResponseEntity getTutors(
            @RequestParam Map<String, String> params,
            @PageableDefault(page = 0, size = 10, sort = "profileId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return getResponseEntity(pageable);
    }

    @GetMapping("/tutees")
    public ResponseEntity getTutees(
            @RequestParam Map<String, String> params,
            @PageableDefault(page = 0, size = 10, sort = "profileId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return getResponseEntity(pageable);
    }


    private ResponseEntity getResponseEntity(
            @PageableDefault(page = 0, size = 10, sort = "profileId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ProfileListResponseDto userResponse = stubData.createProfileListResponse();
        List<ProfileListResponseDto> userList = List.of(userResponse, userResponse, userResponse);
        Page<ProfileListResponseDto> page = new PageImpl<>(userList, pageable, userList.size());
        PageResponseDto response = PageResponseDto.of(userList, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
