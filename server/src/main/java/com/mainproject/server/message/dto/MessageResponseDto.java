package com.mainproject.server.message.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageResponseDto {


    private Long messageId;

    private Long senderId;

    private String senderName;

    private Long receiverId;

    private String receiverName;

    private String messageContent;

    private LocalDateTime createAt;
}
