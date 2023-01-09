package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DateNoticeResponseDto {

    private Long dateNoticeId;
    private LocalDateTime noticeAt;
    private NoticeResponseDto noticeResponseDto;
    private ScheduleResponseDto scheduleResponseDto;
    private List<HomeworkResponseDto> homeworkResponseDtos;
}
