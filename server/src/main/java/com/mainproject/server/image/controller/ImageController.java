package com.mainproject.server.image.controller;


import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.utils.StubData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class ImageController {

    private final StubData stubData;

    @PostMapping("/profile-image/{profileId}")
    public ResponseEntity postProfileImage(
            @PathVariable Long profileId,
            @RequestParam("image") MultipartFile[] multipartFile
    ) {
        ImageResponseDto response = stubData.createImageResponse();
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PatchMapping("/profile-image/{profileId}")
    public ResponseEntity patchProfileImage(
            @PathVariable Long profileId,
            @RequestParam("image") MultipartFile[] multipartFile
    ) {
        ImageResponseDto response = stubData.createImageResponse();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/profile-image/{profileId}")
    public ResponseEntity deleteProfileImage(
            @PathVariable Long profileId
    ) {
        return ResponseEntity.noContent().build();
    }
}
