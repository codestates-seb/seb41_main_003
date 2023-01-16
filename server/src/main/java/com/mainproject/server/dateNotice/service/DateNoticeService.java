package com.mainproject.server.dateNotice.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.NoticeStatus;
import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.entity.Homework;
import com.mainproject.server.dateNotice.entity.Notice;
import com.mainproject.server.dateNotice.entity.Schedule;
import com.mainproject.server.dateNotice.repository.DateNoticeRepository;
import com.mainproject.server.dateNotice.repository.HomeworkRepository;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.service.TutoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class DateNoticeService {

    private final DateNoticeRepository dateNoticeRepository;

    private final TutoringService tutoringService;

    private final HomeworkRepository homeworkRepository;




    public DateNotice createDateNotice(DateNotice dateNotice, Long tutoringId) {
        Tutoring findTutoring = tutoringService.verifiedTutoring(tutoringId);
        findTutoring.setTutoringStatus(TutoringStatus.UNCHECK);
        dateNotice.addTutoring(findTutoring);
        dateNotice.getHomeworks().forEach(h -> h.addDateNotice(dateNotice));
        dateNotice.setNoticeStatus(NoticeStatus.NONE);
        DateNotice saveNotice = dateNoticeRepository.save(dateNotice);
        return updateCheckNotice(saveNotice, findTutoring);
    }

    public DateNotice getDateNotice(Long dateNoticeId) {
        return verifiedDateNoticeById(dateNoticeId);
    }


    public DateNotice updateDateNotice(DateNotice dateNotice) {
        DateNotice findDateNotice = verifiedDateNoticeById(dateNotice.getDateNoticeId());
        Optional.ofNullable(dateNotice.getDateNoticeTitle())
                .ifPresent(findDateNotice::setDateNoticeTitle);
        Optional.ofNullable(dateNotice.getStartTime())
                .ifPresent(findDateNotice::setStartTime);
        Optional.ofNullable(dateNotice.getEndTime())
                .ifPresent(findDateNotice::setEndTime);
        Schedule schedule = dateNotice.getSchedule();
        Schedule findSchedule = findDateNotice.getSchedule();
        Optional.ofNullable(schedule)
                .ifPresent(s -> findSchedule.setScheduleBody(schedule.getScheduleBody()));
        Notice notice = dateNotice.getNotice();
        Notice findNotice = findDateNotice.getNotice();
        Optional.ofNullable(notice)
                .ifPresent(n -> findNotice.setNoticeBody(notice.getNoticeBody()));
        Set<Homework> homeworks = dateNotice.getHomeworks();
        Set<Homework> findHomeworks = findDateNotice.getHomeworks();
        Optional.ofNullable(homeworks)
                .ifPresent( h -> {
                    homeworkRepository.deleteAllById(
                            findHomeworks.stream()
                                    .map(Homework::getHomeworkId)
                                    .collect(Collectors.toList()));
                            findHomeworks.clear();
                    h.forEach(hw -> {
                        // Todo 리팩토링 방법 찾아보자 Mapper 때문에 편의 메소드 두개 다 호출 중
                        hw.addDateNotice(findDateNotice);
                        findDateNotice.addHomework(hw);
                    });
                        });
        updateCheckNotice(findDateNotice,findDateNotice.getTutoring());
        return dateNoticeRepository.save(findDateNotice);
    }

    public void deleteDateNotice(Long dateNoticeId) {
        DateNotice verifiedDateNotice = verifiedDateNoticeById(dateNoticeId);
        dateNoticeRepository.delete(verifiedDateNotice);
    }

    /* 검증 및 유틸 로직 */

    private DateNotice updateCheckNotice(DateNotice dateNotice, Tutoring tutoring) {
        if (!dateNotice.getNotice().getNoticeBody().isBlank()) {
            tutoring.setLatestNoticeId(dateNotice.getNotice().getNoticeId());
            tutoring.setLatestNoticeBody(dateNotice.getNotice().getNoticeBody());
            dateNotice.setNoticeStatus(NoticeStatus.NOTICE);
        } else {
            dateNotice.setNoticeStatus(NoticeStatus.NONE);
        }
        return dateNotice;
    }

    private DateNotice verifiedDateNoticeById(Long dateNoticeId) {
        Optional<DateNotice> optionalDateNotice = dateNoticeRepository.findById(dateNoticeId);

        return optionalDateNotice.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.NOT_FOUND)
        );
    }
}


