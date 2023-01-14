package com.mainproject.server.profile.service;


import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.UserStatus;
import com.mainproject.server.constant.WantedStatus;
import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.image.entity.ProfileImage;
import com.mainproject.server.profile.dto.ProfileListResponseDto;
import com.mainproject.server.profile.dto.ProfilePageDto;
import com.mainproject.server.profile.dto.ProfileQueryDto;
import com.mainproject.server.profile.dto.WantedDto;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.repository.ProfileRepository;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.subject.dto.SubjectDto;
import com.mainproject.server.subject.entity.Subject;
import com.mainproject.server.subject.entity.SubjectProfile;
import com.mainproject.server.subject.repository.SubjectProfileRepository;
import com.mainproject.server.subject.repository.SubjectRepository;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;

    private final SubjectRepository subjectRepository;

    private final SubjectProfileRepository subjectProfileRepository;

    public List<Profile> getProfiles(
            Long userId
    ) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.USER_NOT_FOUND));
        List<Profile> profiles = new ArrayList<>(findUser.getProfiles());
        if (profiles.isEmpty()) {
            return new ArrayList<>();
        }
        return profiles;
    }

    public ProfilePageDto getProfile(Long profileId, Pageable pageable) {
        Profile findProfile = verifiedProfileById(profileId);
        Set<Review> reviews = findProfile.getReviews();
        Page<Review> reviewPage = new PageImpl<>(new ArrayList<>(reviews), pageable, reviews.size());
        return ProfilePageDto.of(findProfile, reviewPage);
    }

    public ProfilePageDto createProfile(
            Long userId,
            Profile profile,
            List<SubjectDto> subjectDtos,
            Pageable pageable
    ) {
        // Todo 하나의 회원에 프로필은 최대 4개만 생성가능하도록 유효성 검증
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.USER_NOT_FOUND));
        UserStatus userStatus = findUser.getUserStatus();
        if (userStatus.equals(UserStatus.NONE)) {
            throw new ServiceLogicException(ErrorCode.USER_TYPE_NOT_NONE);
        }
        ProfileImage image = getBasicImage();
        profile.addUserImage(image);
        profile.setWantedStatus(WantedStatus.NONE);
        profile.setProfileStatus(ProfileStatus.valueOf(userStatus.name()));
        profile.addUser(findUser);
        createSubjectProfile(profile, subjectDtos);
        Profile save = profileRepository.save(profile);
        return getProfilePageDto(save, pageable);
    }

    public ProfilePageDto updateProfile(
            Long profileId,
            Profile profile,
            List<SubjectDto> subjectDtos,
            Pageable pageable
    ) {
        Profile findProfile = verifiedProfileById(profileId);
        subjectProfileRepository.deleteByProfileProfileId(findProfile.getProfileId());
        Profile updateProfile = updateProfileFiled(profile, findProfile);
        createSubjectProfile(updateProfile, subjectDtos);
        Profile save = profileRepository.save(updateProfile);
        return getProfilePageDto(save, pageable);
    }

    public ProfilePageDto updateWantedStatus(Long profileId, WantedDto wantedDto, Pageable pageable) {
        WantedStatus wantedStatus = WantedStatus.valueOf(wantedDto.getWantedStatus());
        Profile findProfile = verifiedProfileById(profileId);
        findProfile.setWantedStatus(wantedStatus);
        Profile save = profileRepository.save(findProfile);
        return getProfilePageDto(save, pageable);
    }

    public void deleteProfile(Long profileId) {
        Profile findProfile = verifiedProfileById(profileId);
        profileRepository.delete(findProfile);
    }

    public void updateProfileForImage(Profile profile) {
        profileRepository.save(profile);
    }

    public PageResponseDto getTutorOrTuteeList(
            Map<String, String> params,
            Pageable defaultPageable
    ) {
        // Todo 프로필의 wantedStatus가 REQUEST 일때만 출력되도록 수정
        try {
            String sort = params.get("sort");
            String[] subjects = params.get("subject").split(",");
            String name = params.get("name");
            String key = params.get("key");
            Pageable pageable = getCustomPageable(defaultPageable, sort);
            Page<ProfileQueryDto> queryProfile = profileRepository.findQueryProfile(
                    key, subjects, name, pageable
            );
            List<ProfileListResponseDto> dtoList = queryProfile.getContent()
                    .stream()
                    .map(ProfileListResponseDto::of)
                    .collect(Collectors.toList());
            return PageResponseDto.of(dtoList, queryProfile);
        } catch (PropertyReferenceException e) {
            throw new ServiceLogicException(ErrorCode.WRONG_SORT_PROPERTY);
        }
    }



    /* 검증 및 유틸 메소드 */

    public Profile verifiedProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.PROFILE_NOT_FOUND));
    }

    private static Pageable getCustomPageable(Pageable defaultPageable, String sort) {
        if (sort != null && sort.equals("rate")) {
            return PageRequest.of(
                    defaultPageable.getPageNumber(),
                    defaultPageable.getPageSize(),
                    Sort.by(Sort.Order.desc(sort))
            );
        } else {
            return PageRequest.of(
                    defaultPageable.getPageNumber(),
                    defaultPageable.getPageSize(),
                    defaultPageable.getSort()
            );
        }
    }

    private void createSubjectProfile(Profile profile, List<SubjectDto> subjectDtos) {
        if (subjectDtos != null) {
            StringBuilder sb = new StringBuilder();
            profile.getSubjectProfiles().clear();
            subjectDtos
                    .forEach(s -> {
                        Subject subject = subjectRepository.findById(s.getSubjectId())
                                .orElseThrow(() -> new ServiceLogicException(ErrorCode.SUBJECT_NOT_FOUND));
                        new SubjectProfile(
                                profile,
                                subject,
                                s.getContent());
                        sb.append(subject.getSubjectTitle()).append(",");
                    });
            String subjectString = sb.toString();
            profile.setSubjectString(subjectString.replaceFirst(".$", ""));
        } else {
            throw new ServiceLogicException(ErrorCode.SUBJECT_NOT_NULL);
        }
    }

    private static ProfilePageDto getProfilePageDto(Profile profile, Pageable pageable) {
        Set<Review> reviews = profile.getReviews();
        Page<Review> reviewPage = new PageImpl<>(new ArrayList<>(reviews), pageable, reviews.size());
        return ProfilePageDto.of(profile, reviewPage);
    }

    private Profile updateProfileFiled(Profile updateProfile, Profile findProfile) {
        Optional.ofNullable(updateProfile.getName()).ifPresent(findProfile::setName);
        Optional.ofNullable(updateProfile.getBio()).ifPresent(findProfile::setBio);
        Optional.ofNullable(updateProfile.getSchool()).ifPresent(findProfile::setSchool);
        Optional.ofNullable(updateProfile.getWay()).ifPresent(findProfile::setWay);
        Optional.ofNullable(updateProfile.getDifference()).ifPresent(findProfile::setDifference);
        Optional.ofNullable(updateProfile.getGender()).ifPresent(findProfile::setGender);
        Optional.ofNullable(updateProfile.getCharacter()).ifPresent(findProfile::setCharacter);
        Optional.ofNullable(updateProfile.getPay()).ifPresent(findProfile::setPay);
        Optional.ofNullable(updateProfile.getWantDate()).ifPresent(findProfile::setWantDate);
        Optional.ofNullable(updateProfile.getPreTutoring()).ifPresent(findProfile::setPreTutoring);
        return findProfile;
    }

    private ProfileImage getBasicImage() {
        return ProfileImage.builder()
                .fileName("basic")
                .url("https://image-test-suyoung.s3.ap-northeast-2.amazonaws.com/image/user.png")
                .build();
    }

}
