package com.mainproject.server.message.dto;

import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.message.entity.MessageRoom;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRoomSimpleResponseDto {

    private Long messageRoomId;

    private String messageStatus;

    private String lastMessage;

    private String targetName;

    private LocalDateTime createAt;

    public MessageRoomSimpleResponseDto(
            ProfileStatus status,
            MessageRoom messageRoom
    ) {
        this.messageRoomId = messageRoom.getMessageRoomId();
        this.messageStatus = messageRoom.getMessageStatus().name();
        if (status.equals(ProfileStatus.TUTEE)) {
            this.targetName = messageRoom.getTutor().getName();
        } else {
            this.targetName = messageRoom.getTutee().getName();
        }
        this.createAt = messageRoom.getCreateAt();
        List<Message> messages = new ArrayList<>(messageRoom.getMessages());
        if (!messages.isEmpty()) {
            this.lastMessage = messages.get(messages.size() - 1)
                    .getMessageContent();
        }
    }

    public static MessageRoomSimpleResponseDto of(
            ProfileStatus status,
            MessageRoom messageRoom
    ) {
        return new MessageRoomSimpleResponseDto(status, messageRoom);
    }

}
