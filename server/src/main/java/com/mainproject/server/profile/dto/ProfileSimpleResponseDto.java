package com.mainproject.server.profile.dto;


import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.subject.dto.SubjectResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileSimpleResponseDto {

    private Long profileId;

    private String name;

    private double rate;

    private List<SubjectResponseDto> subjects;

    private String school;

    private String bio;

    private ImageResponseDto profileImage;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
