package com.mainproject.server.message.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRoomPostDto {

    @NotNull
    private Long tutorId;
    @NotNull
    private  Long tuteeId;
}
