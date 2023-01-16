package com.mainproject.server.profile.dto;


import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.subject.dto.SubjectResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileListResponseDto {

    private Long profileId;

    private String name;

    private double rate;

    private List<SubjectResponseDto> subjects;

    private String school;

    private String bio;

    private ImageResponseDto profileImage;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public ProfileListResponseDto(ProfileQueryDto dto) {
        this.profileId = dto.getProfileId();
        this.name = dto.getName();
        this.rate = dto.getRate();
        this.school = dto.getSchool();
        this.bio = dto.getBio();
        this.profileImage = ImageResponseDto.of(
                dto.getProfileImageId(),
                dto.getProfileImageUrl());
        this.createAt = dto.getCreateAt();
        this.updateAt = dto.getUpdateAt();
        this.subjects = Arrays.stream(dto.getSubjectString().split(","))
                .map(SubjectResponseDto::of)
                .collect(Collectors.toList());
    }

    public static ProfileListResponseDto of(ProfileQueryDto dto) {
        return new ProfileListResponseDto(dto);
    }
}
