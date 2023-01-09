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
}
