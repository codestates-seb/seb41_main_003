package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NoticePatchDto {

    @NotNull
    private Long noticeId;

    private String noticeTitle;

    private String noticeBody;
}
