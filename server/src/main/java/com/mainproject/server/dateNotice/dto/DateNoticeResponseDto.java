package com.mainproject.server.dateNotice.dto;

import com.mainproject.server.dateNotice.entity.DateNotice;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateNoticeResponseDto {

    private Long dateNoticeId;

    private String dateNoticeTitle;

    private String startTime;

    private String endTime;

    private ScheduleResponseDto schedule;

    private NoticeResponseDto notice;

    private List<HomeworkResponseDto> homeworks;

    private String noticeStatus;

    public DateNoticeResponseDto(DateNotice dateNotice) {
        this.dateNoticeId = dateNotice.getDateNoticeId();
        this.dateNoticeTitle = dateNotice.getDateNoticeTitle();
        this.startTime = dateNotice.getStartTime();
        this.endTime = dateNotice.getEndTime();
        this.schedule = new ScheduleResponseDto(
                dateNotice.getSchedule().getScheduleId(),
                dateNotice.getSchedule().getScheduleBody()
        );
        this.notice = new NoticeResponseDto(
                dateNotice.getNotice().getNoticeId(),
                dateNotice.getNotice().getNoticeBody()
        );
        this.homeworks = dateNotice.getHomeworks()
                .stream()
                .map(HomeworkResponseDto::of)
                .collect(Collectors.toList());
        this.noticeStatus = dateNotice.getNoticeStatus().name();
    }

    public static DateNoticeResponseDto of(DateNotice dateNotice) {
        return new DateNoticeResponseDto(dateNotice);
    }
}
