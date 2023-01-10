package com.mainproject.server.tutoring.dto;

import com.mainproject.server.dateNotice.dto.DateNoticeResponseDto;
import com.mainproject.server.profile.dto.ProfileListResponseDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutoringDto {

    private Long tutoringId;

    private String tutoringTitle;

    private String tutoringStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private ProfileListResponseDto tutor;

    private ProfileListResponseDto tutee;

    private ReviewResponseDto review;

    private Page<DateNoticeResponseDto> dateNotices;
}
