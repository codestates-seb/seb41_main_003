package com.mainproject.server.dateNotice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponseDto {

    private Long noticeId;

    private String noticeBody;
}
