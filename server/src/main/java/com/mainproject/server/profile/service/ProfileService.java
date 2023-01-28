package com.mainproject.server.profile.service;


import com.mainproject.server.constant.*;
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
import com.mainproject.server.review.repository.ReviewRepository;
import com.mainproject.server.subject.dto.SubjectDto;
import com.mainproject.server.subject.entity.Subject;
import com.mainproject.server.subject.entity.SubjectProfile;
import com.mainproject.server.subject.repository.SubjectProfileRepository;
import com.mainproject.server.subject.repository.SubjectRepository;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final ReviewRepository reviewRepository;


    public List<Profile> getProfiles(
            Long userId
    ) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.USER_NOT_FOUND));
        List<Profile> profiles = findUser.getProfiles()
                .stream().filter(p -> !(p.getProfileStatus().equals(ProfileStatus.INACTIVE)))
                .collect(Collectors.toList());
        if (profiles.isEmpty()) {
            return new ArrayList<>();
        }
        return profiles;
    }

    public ProfilePageDto getProfile(Long profileId, Pageable pageable) {
        Profile findProfile = verifiedProfileById(profileId);
        return getProfilePageDto(findProfile, pageable);
    }

    public ProfilePageDto createProfile(
            Long userId,
            Profile profile,
            List<SubjectDto> subjectDtos,
            Pageable pageable
    ) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.USER_NOT_FOUND));
        UserStatus userStatus = findUser.getUserStatus();
        if (userStatus.equals(UserStatus.NONE)) {
            throw new ServiceLogicException(ErrorCode.USER_TYPE_NOT_NONE);
        } else if (findUser.getProfiles()
                .stream()
                .filter(p -> !(p.getProfileStatus().equals(ProfileStatus.INACTIVE)))
                .count() >= 4
        ) {
            throw new ServiceLogicException(ErrorCode.EXCEEDED_MAXIMUM_PROFILE_COUNT);
        }
        ProfileImage image = getBasicImage();
        profile.addProfileImage(image);
        profile.setWantedStatus(WantedStatus.NONE);
        profile.setProfileStatus(ProfileStatus.valueOf(userStatus.name()));
        profile.addUser(findUser);
        Profile save = profileRepository.save(createSubjectProfile(profile, subjectDtos));
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
        return getProfilePageDto(createSubjectProfile(updateProfile, subjectDtos), pageable);
    }

    public ProfilePageDto updateWantedStatus(Long profileId, WantedDto wantedDto, Pageable pageable) {
        WantedStatus wantedStatus = WantedStatus.valueOf(
                wantedDto.getWantedStatus().toUpperCase()
        );
        Profile findProfile = verifiedProfileById(profileId);
        if (findProfile.getWantedStatus().equals(WantedStatus.BASIC)) {
            throw new ServiceLogicException(ErrorCode.NOT_CHANGE_WANTED_STATUS);
        }
        findProfile.setWantedStatus(wantedStatus);
        return getProfilePageDto(findProfile, pageable);
    }

    public void deleteProfile(Long profileId) {
        Profile findProfile = verifiedProfileById(profileId);
        findProfile.setProfileStatus(ProfileStatus.INACTIVE);
        findProfile.setWantedStatus(WantedStatus.NONE);
        profileRepository.save(findProfile);
    }

    public void callProfile(Long profileId) {
        Profile findProfile = verifiedProfileById(profileId);
        User user = findProfile.getUser();
        if (user.getProfiles()
                .stream()
                .filter(p -> !(p.getProfileStatus().equals(ProfileStatus.INACTIVE)))
                .count() < 4) {
            findProfile.setProfileStatus(ProfileStatus.valueOf(user.getUserStatus().name()));
        }
        profileRepository.save(findProfile);
    }

    public void delegateSaveProfile(Profile profile) {
        profileRepository.save(profile);
    }

    public PageResponseDto getTutorOrTuteeList(
            Map<String, String> params,
            Pageable defaultPageable
    ) {
        try {
            String sort = params.get("sort");
            String subjectString = params.get("subject");
            String[] subjects = null;
            if (subjectString != null) {
                subjects = subjectString.split(",");
            }
            String search = params.get("search");
            String key = params.get("key");
            Pageable pageable = getCustomPageable(defaultPageable, sort);
            Page<ProfileQueryDto> queryProfile = profileRepository.findQueryProfile(
                    key, subjects, search, WantedStatus.REQUEST, pageable
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

    private Pageable getCustomPageable(Pageable defaultPageable, String sort) {
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

    public Profile createSubjectProfile(Profile profile, List<SubjectDto> subjectDtos) {
        if (subjectDtos != null) {
            StringBuilder sb = new StringBuilder();
            profile.setSubjectProfiles(new LinkedHashSet<>());
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
            return profile;
        } else {
            throw new ServiceLogicException(ErrorCode.SUBJECT_NOT_NULL);
        }
    }

    private ProfilePageDto getProfilePageDto(Profile profile, Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAllByTutor(profile, pageable);
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
        if (findProfile.getWantedStatus().equals(WantedStatus.BASIC)) {
            findProfile.setWantedStatus(WantedStatus.NONE);
        }
        return findProfile;
    }

    public ProfileImage getBasicImage() {
        return ProfileImage.builder()
                .fileName(ImageProperty.BASIC_IMAGE_FILE_NAME.name())
                .url("https://image-test-suyoung.s3.ap-northeast-2.amazonaws.com/image/user.png")
                .build();
    }

}
