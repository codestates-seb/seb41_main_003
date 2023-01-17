package com.mainproject.server.image.controller;


import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.image.entity.ProfileImage;
import com.mainproject.server.image.mapper.ImageMapper;
import com.mainproject.server.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class ImageController {


    private final ImageMapper imageMapper;

    private final ImageService imageService;

    @PostMapping("/profile-image/{profileId}")
    public ResponseEntity postProfileImage(
            @PathVariable Long profileId,
            @RequestParam("image") MultipartFile[] multipartFile
    ) {
        List<ProfileImage> imageList =
                imageService.uploadProfileImage(profileId, multipartFile);
        return new ResponseEntity(
                ResponseDto.of(imageMapper.entityListToImageResponseDtoList(imageList)),
                HttpStatus.CREATED);
    }

    @PatchMapping("/profile-image/{profileId}")
    public ResponseEntity patchProfileImage(
            @PathVariable Long profileId,
            @RequestParam("image") MultipartFile[] multipartFile
    ) {
        List<ProfileImage> imageList =
                imageService.updateProfileImage(profileId, multipartFile);
        return new ResponseEntity(
                ResponseDto.of(imageMapper.entityListToImageResponseDtoList(imageList)),
                HttpStatus.OK);
    }

    @DeleteMapping("/profile-image/{profileId}")
    public ResponseEntity deleteProfileImage(
            @PathVariable Long profileId
    ) {
        imageService.deleteProfileImage(profileId);
        return ResponseEntity.noContent().build();
    }
}
