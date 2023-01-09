package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateNoticePostDto {
    private Long dateNoticeId;
    private String scheduleTitle;
    private String scheduleBody;
    private String noticeTitle;
    private String noticeBody;

    private List<String> Homeworks;

}
