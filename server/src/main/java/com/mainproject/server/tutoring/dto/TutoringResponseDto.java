package com.mainproject.server.tutoring.dto;

import com.mainproject.server.dateNotice.dto.DateNoticeResponseDto;
import com.mainproject.server.profile.dto.ProfileResponseDto;
import com.mainproject.server.profile.dto.ProfileSimpleResponseDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TutoringResponseDto {

    private Long tutoringId;

    private String tutoringStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private ProfileSimpleResponseDto tutor;

    private ProfileSimpleResponseDto tutee;

    private ReviewResponseDto review;

    private List<DateNoticeResponseDto> dateNotices;
}
