package com.mainproject.server.image.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ImageProperty;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.image.entity.ProfileImage;
import com.mainproject.server.image.repository.ImageRepository;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final ImageRepository imageRepository;

    private final AmazonS3 amazonS3;

    private final ProfileService profileService;

    public List<ProfileImage> uploadProfileImage(
            Long profileId,
            MultipartFile[] multipartFile
    ) {
        Profile findProfile = profileService.verifiedProfileById(profileId);
        List<ProfileImage> list = getProfileImages(multipartFile, findProfile);
        return list;
    }

    public List<ProfileImage> updateProfileImage(
            Long profileId,
            MultipartFile[] multipartFile
    ) {
        Profile findProfile = profileService.verifiedProfileById(profileId);
        ProfileImage profileImage = findProfile.getProfileImage();
        String fileName = profileImage.getFileName();
        if (!fileName.equals(ImageProperty.BASIC_IMAGE_FILE_NAME.getName())) {
            deleteS3(profileImage.getFileName());
        }
        imageRepository.deleteById(profileImage.getProfileImageId());
        List<ProfileImage> list = getProfileImages(multipartFile, findProfile);
        return list;
    }

    public void deleteProfileImage(Long profileId) {
        Profile findProfile = profileService.verifiedProfileById(profileId);
        ProfileImage profileImage = findProfile.getProfileImage();
        deleteS3(profileImage.getFileName());
        imageRepository.delete(profileImage);
        findProfile.setProfileImage(getBasicImage());
        profileService.delegateSaveProfile(findProfile);
    }


    /* 유틸 메소드 */
    private ProfileImage upload(File uploadFile, String dirName) {
        String s3FileName = UUID.randomUUID() + "-" + uploadFile.getName();
        String fileName = dirName + "/" + s3FileName;
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        ProfileImage fileEntity = new ProfileImage();
        fileEntity.setUrl(uploadImageUrl);
        fileEntity.setFileName(fileName);
        return fileEntity;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        // publicRead 권한으로 업로드
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void deleteS3(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("로컬 저장 파일 삭제 완료.");
        }else {
            log.info("로컬 저장 파일 삭제 실패.");
        }
    }

    private List<ProfileImage> getProfileImages(
            MultipartFile[] multipartFile,
            Profile findProfile
    ) {
        List<ProfileImage> list = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            File uploadFile = convert(file)
                    .orElseThrow(() -> new ServiceLogicException(ErrorCode.FILE_CONVERT_ERROR));
            ProfileImage fileEntity = upload(
                    uploadFile,
                    ImageProperty.BASIC_IMAGE_DIR_NAME.getName());
            ProfileImage save = imageRepository.save(fileEntity);
            list.add(fileEntity);
            findProfile.addProfileImage(save);
        }
        profileService.delegateSaveProfile(findProfile);
        return list;
    }

    private Optional<File> convert(MultipartFile file) {
        String fileName = Optional.ofNullable(file.getOriginalFilename())
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.FILE_NOT_NULL));
        File convertFile = new File(fileName);
        try {
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            throw new ServiceLogicException(ErrorCode.FILE_CONVERT_ERROR);
        }

        return Optional.empty();
    }

    public ProfileImage getBasicImage() {
        return ProfileImage.builder()
                .fileName(ImageProperty.BASIC_IMAGE_FILE_NAME.name())
                .url("https://image-test-suyoung.s3.ap-northeast-2.amazonaws.com/image/user.png")
                .build();
    }

}
