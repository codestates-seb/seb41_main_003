package com.mainproject.server.dateNotice.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.NoticeStatus;
import com.mainproject.server.constant.UserStatus;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.repository.DateNoticeRepository;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.tutoring.service.TutoringService;
import com.mainproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class DateNoticeService {
    private final DateNoticeRepository dateNoticeRepository;
    private final TutoringService tutoringService;
    private final UserService userService;

    public DateNotice createDateNotice(DateNotice dateNotice) {
        tutoringService.setTutoringStatusUncheck(dateNotice.getTutoring().getTutoringId());

        updateNotice(dateNotice);

        DateNotice saveDateNotice = dateNoticeRepository.save(dateNotice);
        return saveDateNotice;
    }

    public DateNotice findDateNotice(Long dateNoticeId, String email) {
        DateNotice verifiedDateNotice = findVerifiedDateNoticeById(dateNoticeId);

        if (userService.verifiedUserByEmail(email).getUserStatus().equals(UserStatus.TUTEE)) {
            tutoringService.setTutoringStatusProgress(
                    verifiedDateNotice.getTutoring().getTutoringId(),
                    PageRequest.of(0,5)
            );
        }

        return verifiedDateNotice;
    }
    
    public DateNotice updateDateNotice(DateNotice dateNotice) {
        DateNotice findDateNotice = findVerifiedDateNoticeById(dateNotice.getDateNoticeId());

        updateNotice(dateNotice);

        Optional.ofNullable(dateNotice.getDateNoticeTitle())
                .ifPresent(findDateNotice::setDateNoticeTitle);
        Optional.ofNullable(dateNotice.getStartTime())
                .ifPresent(findDateNotice::setStartTime);
        Optional.ofNullable(dateNotice.getEndTime())
                .ifPresent(findDateNotice::setEndTime);
        Optional.ofNullable(dateNotice.getSchedule())
                .ifPresent(findDateNotice::setSchedule);
        Optional.ofNullable(dateNotice.getNotice())
                .ifPresent(findDateNotice::setNotice);
        Optional.ofNullable(dateNotice.getHomeworks())
                .ifPresent(findDateNotice::setHomeworks);

        return dateNoticeRepository.save(findDateNotice);
    }

    public void deleteDateNotice(Long dateNoticeId) {
        DateNotice verifiedDateNotice = findVerifiedDateNoticeById(dateNoticeId);

        dateNoticeRepository.delete(verifiedDateNotice);
    }

    private void updateNotice(DateNotice dateNotice) {
        if (dateNotice.getNotice().getNoticeBody().trim().equals("")) {
            tutoringService.updateLatestNoticeBody(dateNotice.getTutoring(), dateNotice.getNotice().getNoticeBody());
            dateNotice.setNoticeStatus(NoticeStatus.NOTICE);
        }
        dateNoticeRepository.save(dateNotice);
    }

    private DateNotice findVerifiedDateNoticeById(Long dateNoticeId) {
        Optional<DateNotice> optionalDateNotice = dateNoticeRepository.findById(dateNoticeId);
        DateNotice dateNotice = optionalDateNotice.orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));

        return dateNotice;
    }
}


