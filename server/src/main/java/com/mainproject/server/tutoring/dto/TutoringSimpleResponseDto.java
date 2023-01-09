package com.mainproject.server.tutoring.dto;

import com.mainproject.server.dateNotice.dto.DateNoticeResponseDto;
import com.mainproject.server.profile.dto.ProfileSimpleResponseDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TutoringSimpleResponseDto {

    private Long tutoringId;

    private String tutorName;

    private String tuteeName;

    private String tutoringTitle;

    private String tutoringStatus;

    private LocalDateTime createAt;

}
