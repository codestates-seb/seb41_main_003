package com.mainproject.server.user.controller;

import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.user.dto.UserPatchDto;
import com.mainproject.server.user.dto.UserPostDto;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.mapper.UserMapper;
import com.mainproject.server.user.service.UserService;
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
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {


    private final UserMapper userMapper;

    private final UserService userService;

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity getUser(
            @PathVariable @Positive Long userId
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(
                        userMapper.entityToUserResponseDto(
                                userService.getUser(userId)
                        )),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postUser(
            @RequestBody @Valid UserPostDto userPostDto
    ) {
        User saveUser = userService.createUser(
                userMapper.userPostDtoToEntity(userPostDto));
        return new ResponseEntity<>(
                ResponseDto.of(
                        userMapper.entityToUserResponseDto(
                                saveUser
                        )),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(
            @PathVariable @Positive Long userId,
            @RequestBody @Valid UserPatchDto userPatchDto
    ) {
        userPatchDto.setUserId(userId);
        if (userPatchDto.getUserStatus() != null && !userPatchDto.getUserStatus().isBlank()) {
            userPatchDto.setUserStatus(userPatchDto.getUserStatus().toUpperCase());
        }
        User updateUser = userService.updateUser(
                userMapper.userPatchDtoToEntity(userPatchDto));
        return new ResponseEntity<>(
                ResponseDto.of(
                        userMapper.entityToUserResponseDto(
                                updateUser
                        )),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(
            @PathVariable @Positive Long userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tutors")
    public ResponseEntity getTutors(
            @RequestParam Map<String, String> params,
            @PageableDefault(page = 0, size = 20, sort = "updateAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        params.put("key", "TUTOR");
        PageResponseDto response = profileService.getTutorOrTuteeList(params, pageable);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/tutees")
    public ResponseEntity getTutees(
            @RequestParam Map<String, String> params,
            @PageableDefault(page = 0, size = 20, sort = "updateAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        params.put("key", "TUTEE");
        PageResponseDto response = profileService.getTutorOrTuteeList(params, pageable);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
