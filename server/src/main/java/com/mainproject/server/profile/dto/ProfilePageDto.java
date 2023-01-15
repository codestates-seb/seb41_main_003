package com.mainproject.server.profile.dto;

import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.review.dto.ReviewResponseDto;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.subject.dto.SubjectProfileResponseDto;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfilePageDto {

    private Long profileId;

    private String name;

    private double rate;

    private String bio;

    private String school;

    private String wantedStatus;

    private String profileStatus;

    private String way;

    private List<SubjectProfileResponseDto> subjects;

    private String difference;

    private String gender;

    private String pay;

    private String wantDate;

    private String preTutoring;

    private ImageResponseDto profileImage;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Page<ReviewResponseDto> reviews;

    public ProfilePageDto(Profile profile, Page<Review> reviews) {
        this.profileId = profile.getProfileId();
        this.name = profile.getName();
        this.rate = profile.getRate();
        this.bio = profile.getBio();
        this.school = profile.getSchool();
        this.wantedStatus = profile.getWantedStatus().name();
        this.profileStatus = profile.getProfileStatus().name();
        this.way = profile.getWay();
        if (!profile.getSubjectProfiles().isEmpty()) {
            this.subjects = profile.getSubjectProfiles()
                    .stream()
                    .map(p -> SubjectProfileResponseDto.builder()
                            .subjectId(p.getSubject().getSubjectId())
                            .subjectTitle(p.getSubject().getSubjectTitle())
                            .content(p.getContent())
                            .createAt(p.getSubject().getCreateAt())
                            .updateAt(p.getSubject().getUpdateAt())
                            .build()
                    ).collect(Collectors.toList());
        } else this.subjects = new ArrayList<>();
        this.difference = profile.getDifference();
        this.gender = profile.getGender();
        this.pay = profile.getPay();
        this.wantDate = profile.getWantDate();
        this.preTutoring = profile.getPreTutoring();
        if (profile.getProfileImage() != null) {
            this.profileImage = ImageResponseDto.builder()
                    .profileImageId(profile.getProfileImage().getProfileImageId())
                    .url(profile.getProfileImage().getUrl())
                    .createAt(profile.getProfileImage().getCreateAt())
                    .updateAt(profile.getProfileImage().getUpdateAt())
                    .build();
        }
        this.createAt = profile.getCreateAt();
        this.updateAt = profile.getUpdateAt();
        if (!reviews.getContent().isEmpty()) {
            List<ReviewResponseDto> reviewList = reviews.getContent()
                    .stream()
                    .map(r -> ReviewResponseDto.builder()
                            .reviewId(r.getReviewId())
                            .professional(r.getProfessional())
                            .readiness(r.getReadiness())
                            .explanation(r.getExplanation())
                            .punctuality(r.getPunctuality())
                            .reviewBody(r.getReviewBody())
                            .tuteeName(r.getTutee().getName())
                            .createAt(r.getCreateAt())
                            .updateAt(r.getUpdateAt())
                            .build())
                    .collect(Collectors.toList());
            this.reviews = new PageImpl<>(
                    reviewList,
                    reviews.getPageable(),
                    reviewList.size());
        }
        this.reviews = new PageImpl<>(List.of(), reviews.getPageable(), reviews.getTotalElements());
    }

    public static ProfilePageDto of(Profile profile, Page<Review> reviews) {
        return new ProfilePageDto(profile, reviews);
    }
}
