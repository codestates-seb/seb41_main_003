package com.mainproject.server.profile.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WantedDto {
    @NotBlank
    private String wantedStatus;
}
