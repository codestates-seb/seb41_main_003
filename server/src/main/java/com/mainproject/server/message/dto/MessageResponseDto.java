package com.mainproject.server.message.dto;

import com.mainproject.server.message.entity.Message;
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

    public MessageResponseDto(
            Message msg
    ) {
        this.messageId = msg.getMessageId();
        this.senderId = msg.getSender().getProfileId();
        this.receiverId = msg.getReceiver().getProfileId();
        this.senderName = msg.getSender().getName();
        this.receiverName = msg.getReceiver().getName();
        this.messageContent = msg.getMessageContent();
        this.createAt = msg.getCreateAt();
    }

    public static MessageResponseDto of(Message msg) {
        return new MessageResponseDto(msg);
    }
}
