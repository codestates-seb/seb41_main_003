package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DateNoticePatchDto {

    private Long dateNoticeId;

    private String dateNoticeTitle;

    private String startTime;

    private String endTime;

    private String scheduleBody;

    private String noticeBody;

    private List<HomeworkDto> Homeworks;
}
