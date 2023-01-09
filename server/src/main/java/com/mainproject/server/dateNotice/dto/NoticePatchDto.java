package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticePatchDto {

    private Long noticeId;

    private String noticeTitle;

    private String noticeBody;
}
