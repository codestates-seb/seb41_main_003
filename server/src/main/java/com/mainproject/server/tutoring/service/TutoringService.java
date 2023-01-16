package com.mainproject.server.tutoring.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.message.entity.MessageRoom;
import com.mainproject.server.message.service.MessageService;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.tutoring.dto.TutoringDto;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.repository.TutoringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class TutoringService {
    private final TutoringRepository tutoringRepository;
    private final ProfileService profileService;

    private final MessageService messageService;

    public Tutoring createTutoring(Tutoring tutoring, Long profileId, Long messageRoomId) {
        tutoring.setTutoringStatus(getTutoringStatus(profileId));
        Profile tutor = profileService.verifiedProfileById(tutoring.getTutor().getProfileId());
        Profile tutee = profileService.verifiedProfileById(tutoring.getTutee().getProfileId());
        tutoring.addTutor(tutor);
        tutoring.addTutee(tutee);
        Tutoring save = tutoringRepository.save(tutoring);
        MessageRoom messageRoom = messageService.updateMessageRoom(messageRoomId, save.getTutoringId());
        messageService.sendTutoringRequestMessage(profileId, messageRoom, tutor, tutee);
        return save;
    }

    public Page<Tutoring> getAllTutoring(
            Map<String, String> params,
            Long profileId,
            Pageable pageable
    ) {
        try {
            if (params.isEmpty())
                throw new ServiceLogicException(ErrorCode.NOT_NULL_WRONG_PROFILE_STATUS_PROPERTY);
            String get = params.get("get");
            ProfileStatus profileStatus = profileService
                    .verifiedProfileById(profileId)
                    .getProfileStatus();
            if (TutoringStatus.valueOf(get).equals(TutoringStatus.FINISH)) {
                params.put("check",TutoringStatus.FINISH.name());
            } else if (TutoringStatus.valueOf(get).equals(TutoringStatus.PROGRESS)) {
                params.put("check",TutoringStatus.UNCHECK.name());
            }
            if (profileStatus.equals(ProfileStatus.TUTEE)) {
                return tutoringRepository
                        .findAllByTuteeProfileIdAndTutoringStatusOrTuteeProfileIdAndTutoringStatus(
                        profileId,
                        TutoringStatus.valueOf(get),
                        profileId,
                        TutoringStatus.valueOf(params.get("check")),
                        pageable);
            } else {
                return tutoringRepository
                        .findAllByTutorProfileIdAndTutoringStatusOrTutorProfileIdAndTutoringStatus(
                        profileId,
                        TutoringStatus.valueOf(get),
                        profileId,
                        TutoringStatus.valueOf(params.get("check")),
                        pageable);
            }
        } catch (IllegalArgumentException e) {
            throw new ServiceLogicException(ErrorCode.NOT_NULL_WRONG_PROFILE_STATUS_PROPERTY);
        }

    }

    public TutoringDto getTutoring(Long tutoringId, Long profileId, Pageable pageable) {
        Tutoring tutoring = verifiedTutoring(tutoringId);
        if (tutoring.getTutee().getProfileId().equals(profileId) &&
                !tutoring.getTutoringStatus().equals(TutoringStatus.FINISH)
        ) {
            tutoring.setTutoringStatus(TutoringStatus.PROGRESS);
            Tutoring progressTutoring = tutoringRepository.save(tutoring);
            return TutoringDto.of(progressTutoring, pageable);
        } else {
            return TutoringDto.of(tutoring, pageable);
        }
    }

    public TutoringDto updateTutoring(Tutoring tutoring, Long tutoringId, Pageable pageable) {
        Tutoring findTutoring = verifiedTutoring(tutoringId);
        Optional.ofNullable(tutoring.getTutoringTitle())
                .ifPresent(findTutoring::setTutoringTitle);
        Optional.ofNullable(tutoring.getTutoringStatus())
                .ifPresent(findTutoring::setTutoringStatus);
        return TutoringDto.of(tutoringRepository.save(findTutoring), pageable);
    }

    public void deleteTutoring(Long tutoringId) {
        tutoringRepository.delete(verifiedTutoring(tutoringId));
    }

    /* 검증 및 유틸 로직 */

    public TutoringDto setTutoringStatusProgress(
            Long tutoringId,
            Long profileId,
            Pageable pageable
    ) {
        Tutoring tutoring = verifiedTutoring(tutoringId);
        if (tutoring.getTutor().getProfileId().equals(profileId) ||
                tutoring.getTutee().getProfileId().equals(profileId) &&
                !tutoring.getTutoringStatus().equals(TutoringStatus.FINISH)
        ) {
            tutoring.setTutoringStatus(TutoringStatus.PROGRESS);
            Tutoring progressTutoring = tutoringRepository.save(tutoring);
            return TutoringDto.of(progressTutoring, pageable);
        } else {
            throw new ServiceLogicException(ErrorCode.ACCESS_DENIED);
        }
    }

    public Tutoring verifiedTutoring(Long tutoringId) {
        return tutoringRepository.findById(tutoringId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));
    }

    private TutoringStatus getTutoringStatus(Long profileId) {
        Profile profile = profileService.verifiedProfileById(profileId);
        if (profile.getProfileStatus().equals(ProfileStatus.TUTEE)) {
            return TutoringStatus.TUTOR_WAITING;
        } else {
            return TutoringStatus.TUTEE_WAITING;
        }
    }
}
