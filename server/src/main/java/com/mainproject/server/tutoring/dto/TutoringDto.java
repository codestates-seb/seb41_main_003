package com.mainproject.server.tutoring.dto;

import com.mainproject.server.dateNotice.dto.DateNoticeResponseDto;
import com.mainproject.server.profile.dto.ProfileListResponseDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import lombok.*;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutoringDto {

    @NotBlank
    private Long tutoringId;

    @NotBlank
    private String tutoringTitle;

    @NotBlank
    private String tutoringStatus;

    @NotBlank
    private LocalDateTime createAt;

    @NotBlank
    private LocalDateTime updateAt;

    @NotBlank
    private ProfileListResponseDto tutor;

    @NotBlank
    private ProfileListResponseDto tutee;

    private ReviewResponseDto review;

    private Page<DateNoticeResponseDto> dateNotices;
}
