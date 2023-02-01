package com.mainproject.server.profile.dto;

import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import com.mainproject.server.subject.dto.SubjectProfileResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
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

    private String profileStatus;

    private String way;

    private String character;

    private List<SubjectProfileResponseDto> subjects;

    private String difference;

    private String gender;

    private String pay;

    private String wantDate;

    private String preTutoring;

    private ImageResponseDto profileImage;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<ReviewResponseDto> reviews;

    public ProfileResponseDto(ProfilePageDto dto) {
        this.profileId = dto.getProfileId();
        this.name = dto.getName();
        this.rate = dto.getRate();
        this.bio = dto.getBio();
        this.school = dto.getSchool();
        this.wantedStatus = dto.getWantedStatus();
        this.profileStatus = dto.getProfileStatus();
        this.way = dto.getWay();
        this.character = dto.getCharacter();
        this.subjects = dto.getSubjects();
        this.difference = dto.getDifference();
        this.gender = dto.getGender();
        this.pay = dto.getPay();
        this.wantDate = dto.getWantDate();
        this.preTutoring = dto.getPreTutoring();
        this.profileImage = dto.getProfileImage();
        this.createAt = dto.getCreateAt();
        this.updateAt= dto.getUpdateAt();
        this.reviews = dto.getReviews().getContent();
    }

    public static ProfileResponseDto of(ProfilePageDto dto) {
        return new ProfileResponseDto(dto);
    }

}
