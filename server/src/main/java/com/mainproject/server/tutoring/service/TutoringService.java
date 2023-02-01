package com.mainproject.server.tutoring.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.repository.DateNoticeRepository;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.message.entity.MessageRoom;
import com.mainproject.server.message.service.MessageService;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.tutoring.dto.TutoringDto;
import com.mainproject.server.tutoring.dto.TutoringQueryDto;
import com.mainproject.server.tutoring.dto.TutoringSimpleResponseDto;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.repository.TutoringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class TutoringService {
    private final TutoringRepository tutoringRepository;

    private final DateNoticeRepository dateNoticeRepository;
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

    public Page<TutoringSimpleResponseDto> getAllTutoring(
            Map<String, String> params,
            Long profileId,
            Pageable pageable
    ) {
        try {
            if (params.isEmpty())
                throw new ServiceLogicException(ErrorCode.NOT_NULL_WRONG_PROFILE_STATUS_PROPERTY);

            Page<TutoringQueryDto> dto = tutoringRepository
                    .findQueryTutoring(profileId, TutoringStatus.valueOf(params.get("get")), pageable);

            List<TutoringSimpleResponseDto> list = dto.getContent()
                    .stream()
                    .map(TutoringSimpleResponseDto::of)
                    .collect(Collectors.toList());

            return new PageImpl<>(list, dto.getPageable(), dto.getTotalElements());
        } catch (IllegalArgumentException e) {
            throw new ServiceLogicException(ErrorCode.NOT_NULL_WRONG_PROFILE_STATUS_PROPERTY);
        }

    }

    public TutoringDto getTutoring(Long tutoringId, Long profileId, Pageable pageable) {
        Tutoring tutoring = verifiedTutoring(tutoringId);
        if (tutoring.getTutee().getProfileId().equals(profileId) &&
                tutoring.getTutoringStatus().equals(TutoringStatus.UNCHECK)
        ) {
            tutoring.setTutoringStatus(TutoringStatus.PROGRESS);
            Tutoring progressTutoring = tutoringRepository.save(tutoring);
            return getTutoringDto(progressTutoring, pageable);
        } else if (tutoring.getTutee().getProfileId().equals(profileId) ||
                tutoring.getTutor().getProfileId().equals(profileId)
        ) {
            return getTutoringDto(tutoring, pageable);
        } else {
            throw new ServiceLogicException(ErrorCode.ACCESS_DENIED);
        }
    }

    public TutoringDto updateTutoring(Tutoring tutoring, Long tutoringId, Pageable pageable) {
        Tutoring findTutoring = verifiedTutoring(tutoringId);
        Optional.ofNullable(tutoring.getTutoringTitle())
                .ifPresent(findTutoring::setTutoringTitle);
        TutoringStatus tutoringStatus = tutoring.getTutoringStatus();
        if (tutoringStatus != null && (
                        tutoringStatus.equals(TutoringStatus.PROGRESS) ||
                        tutoringStatus.equals(TutoringStatus.WAIT_FINISH) ||
                        tutoringStatus.equals(TutoringStatus.FINISH) ||
                        tutoringStatus.equals(TutoringStatus.UNCHECK) ||
                        tutoringStatus.equals(TutoringStatus.TUTOR_DELETE) ||
                        tutoringStatus.equals(TutoringStatus.TUTEE_DELETE)
        )) {
            if (findTutoring.getTutoringStatus().name().contains("DELETE")) {
                findTutoring.setTutoringStatus(TutoringStatus.ALL_DELETE);
            } else {
                findTutoring.setTutoringStatus(tutoringStatus);
            }
        } else {
            throw new ServiceLogicException(ErrorCode.WRONG_STATUS_PROPERTY);
        }
        return getTutoringDto(tutoringRepository.save(findTutoring), pageable);
    }

    public void deleteTutoring(Long tutoringId) {
        Tutoring tutoring = verifiedTutoring(tutoringId);
        if (tutoring.getTutoringStatus().equals(TutoringStatus.TUTEE_WAITING) ||
                tutoring.getTutoringStatus().equals(TutoringStatus.TUTOR_WAITING)
        ) {
            messageService.deleteMessageRoomTutoringId(tutoringId);
            tutoringRepository.delete(tutoring);
        } else {
            throw new ServiceLogicException(ErrorCode.TUTORING_STATUS_BAD_REQUEST);
        }
    }

    /* 검증 및 유틸 로직 */

    private TutoringDto getTutoringDto(Tutoring tutoring, Pageable pageable) {
        Page<DateNotice> dateNoticePage =
                dateNoticeRepository.findAllByTutoring(tutoring, pageable);
        return TutoringDto.of(tutoring, dateNoticePage);
    }

    public TutoringDto setTutoringStatusProgress(
            Long tutoringId,
            Long profileId,
            Pageable pageable
    ) {
        Tutoring tutoring = verifiedTutoring(tutoringId);
        if ((tutoring.getTutor().getProfileId().equals(profileId) ||
                tutoring.getTutee().getProfileId().equals(profileId)) &&
                !tutoring.getTutoringStatus().equals(TutoringStatus.FINISH) &&
                !tutoring.getTutoringStatus().equals(TutoringStatus.PROGRESS)
        ) {
            tutoring.setTutoringStatus(TutoringStatus.PROGRESS);
            Tutoring progressTutoring = tutoringRepository.save(tutoring);
            return getTutoringDto(progressTutoring, pageable);
        } else {
            throw new ServiceLogicException(ErrorCode.TUTORING_STATUS_BAD_REQUEST);
        }
    }

    public Tutoring verifiedTutoring(Long tutoringId) {
        return tutoringRepository.findById(tutoringId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));
    }

    public TutoringStatus getTutoringStatus(Long profileId) {
        Profile profile = profileService.verifiedProfileById(profileId);
        if (profile.getProfileStatus().equals(ProfileStatus.TUTEE)) {
            return TutoringStatus.TUTOR_WAITING;
        } else {
            return TutoringStatus.TUTEE_WAITING;
        }
    }
}
