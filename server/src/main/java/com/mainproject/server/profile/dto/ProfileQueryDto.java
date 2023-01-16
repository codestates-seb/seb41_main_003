package com.mainproject.server.profile.dto;

import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.WantedStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileQueryDto {

    private Long profileId;

    private ProfileStatus profileStatus;

    private WantedStatus wantedStatus;

    private String name;

    private double rate;

    private String school;

    private String bio;

    private Long profileImageId;

    private String profileImageUrl;

    private String subjectString;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
