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
public class MessagePostDto {

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    private Long messageRoomId;

    @NotBlank
    private String messageContent;
}
