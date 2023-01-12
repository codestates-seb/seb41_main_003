package com.mainproject.server.dateNotice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class DateNoticeResponseDto {

    private Long dateNoticeId;

    private String dateNoticeTitle;

    private String startTime;

    private String endTime;

    private ScheduleResponseDto schedule;

    private NoticeResponseDto notice;

    private List<HomeworkResponseDto> homeworks;

    private String noticeStatus;
}
