package com.mainproject.server.user.controller;

import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.profile.dto.ProfileListResponseDto;
import com.mainproject.server.user.dto.UserPatchDto;
import com.mainproject.server.user.dto.UserPostDto;
import com.mainproject.server.user.dto.UserResponseDto;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.mapper.UserMapper;
import com.mainproject.server.user.service.UserService;
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

    private final UserMapper userMapper;

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity getUser(
            @PathVariable Long userId
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
            @RequestBody @Validated UserPostDto userPostDto
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
            @PathVariable Long userId,
            @RequestBody UserPatchDto userPatchDto
    ) {
        userPatchDto.setUserId(userId);
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
            @PathVariable Long userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tutors")
    public ResponseEntity getTutors(
            @RequestParam Map<String, String> params,
            @PageableDefault(page = 0, size = 20, sort = "profileId")
            Pageable pageable
    ) {
        params.put("key", "TUTOR");
        return getResponseEntity(pageable);
    }

    @GetMapping("/tutees")
    public ResponseEntity getTutees(
            @RequestParam Map<String, String> params,
            @PageableDefault(page = 0, size = 20, sort = "profileId")
            Pageable pageable
    ) {
        params.put("key", "TUTEE");
        return getResponseEntity(pageable);
    }


    private ResponseEntity getResponseEntity(
            Pageable pageable
    ) {
        ProfileListResponseDto userResponse = stubData.createProfileListResponse();
        List<ProfileListResponseDto> userList = List.of(userResponse, userResponse, userResponse);
        Page<ProfileListResponseDto> page = new PageImpl<>(userList, pageable, userList.size());
        PageResponseDto response = PageResponseDto.of(userList, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
