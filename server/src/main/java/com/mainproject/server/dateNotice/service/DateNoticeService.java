package com.mainproject.server.dateNotice.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.repository.DateNoticeRepository;
import com.mainproject.server.exception.ServiceLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class DateNoticeService {
    private final DateNoticeRepository dateNoticeRepository;

    public DateNotice createDateNotice(DateNotice dateNotice) {
        DateNotice saveDateNotice = dateNoticeRepository.save(dateNotice);
        return saveDateNotice;
    }

    public DateNotice findDateNotice(Long dateNoticeId) {
        DateNotice verifiedDateNotice = findVerifiedDateNoticeById(dateNoticeId);

        return verifiedDateNotice;
    }
    
    public DateNotice updateDateNotice(DateNotice dateNotice) {
        DateNotice findDateNotice = findDateNotice(dateNotice.getDateNoticeId());

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

    private DateNotice findVerifiedDateNoticeById(Long dateNoticeId) {
        Optional<DateNotice> optionalDateNotice = dateNoticeRepository.findById(dateNoticeId);
        DateNotice dateNotice = optionalDateNotice.orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));

        return dateNotice;
    }
}


