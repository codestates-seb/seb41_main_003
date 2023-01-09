package com.mainproject.server.profile.dto;

import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import com.mainproject.server.subject.dto.SubjectProfileResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileResponseDto {

    private Long profileId;

    private String name;

    private double rate;

    private String bio;

    private String school;

    private String wantedStatus;

    private String way;

    private List<SubjectProfileResponseDto> subjects;

    private String difference;

    private String gender;

    private String pay;

    private String wantDate;

    private String preTutoring;

    private List<ReviewResponseDto> reviews;

    private ImageResponseDto profileImage;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
