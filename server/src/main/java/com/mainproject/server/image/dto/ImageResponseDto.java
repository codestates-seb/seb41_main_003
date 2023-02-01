package com.mainproject.server.image.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageResponseDto {

    private Long profileImageId;

    private String url;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public ImageResponseDto(Long profileImageId, String url) {
        this.profileImageId = profileImageId;
        this.url = url;
        this.createAt = null;
        this.updateAt = null;
    }

    public static ImageResponseDto of(Long profileImageId, String url) {
        return new ImageResponseDto(profileImageId, url);
    }
}
