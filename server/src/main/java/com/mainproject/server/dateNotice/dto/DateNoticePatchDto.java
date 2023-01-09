package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DateNoticePatchDto {
    private Long dateNoticeId;
    private LocalDateTime noticeAt;
    private NoticePatchDto noticePatchDto;
    private SchedulePatchDto schedulePatchDto;
    private List<HomeworkPatchDto> homeworkPatchDtos;
}
