package com.mainproject.server.message.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessagePostDto {

    private Long senderId;

    private Long receiverId;

    private Long messageResponseId;

    private String messageContent;
}
