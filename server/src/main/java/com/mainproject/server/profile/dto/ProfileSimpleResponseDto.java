package com.mainproject.server.profile.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileSimpleResponseDto {

    private Long profileId;

    private String name;

    private String url;
}
