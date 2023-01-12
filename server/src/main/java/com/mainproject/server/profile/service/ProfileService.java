package com.mainproject.server.profile.service;


import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.WantedStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.profile.dto.ProfilePageDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.USER_NOT_FOUND));
        profile.setWantedStatus(WantedStatus.NONE);
        profile.setProfileStatus(ProfileStatus.valueOf(findUser.getUserStatus().name()));
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



    /* 검증 및 유틸 메소드 */

    public Profile verifiedProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.PROFILE_NOT_FOUND));
    }

    private void createSubjectProfile( Profile profile, List<SubjectDto> subjectDtos) {
        if (subjectDtos != null) {
            profile.getSubjectProfiles().clear();
            subjectDtos
                    .forEach(s -> {
                        Subject subject = subjectRepository.findById(s.getSubjectId())
                                .orElseThrow(() -> new ServiceLogicException(ErrorCode.SUBJECT_NOT_FOUND));
                        new SubjectProfile(
                                profile,
                                subject,
                                s.getContent());
                    });
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

}
