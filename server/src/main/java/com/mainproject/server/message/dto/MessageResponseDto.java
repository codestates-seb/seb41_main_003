package com.mainproject.server.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    private Long messageRoomId;

    private Long messageId;

    private Long senderId;

    private String senderName;

    private Long receiverId;

    private String receiverName;

    private String messageContent;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createAt;

    public MessageResponseDto(
            Message msg
    ) {
        this.messageRoomId = msg.getMessageRoom().getMessageRoomId();
        this.messageId = msg.getMessageId();
        this.senderId = msg.getSender().getProfileId();
        this.receiverId = msg.getReceiver().getProfileId();
        this.senderName = msg.getSender().getName();
        this.receiverName = msg.getReceiver().getName();
        this.messageContent = msg.getMessageContent();
        this.createAt = msg.getCreateAt();
    }

    public MessageResponseDto(
            Message msg, Long messageRoomId
    ) {
        this.messageRoomId = messageRoomId;
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
    public static MessageResponseDto of(Message msg, Long messageRoomIdo) {
        return new MessageResponseDto(msg, messageRoomIdo);
    }
}
