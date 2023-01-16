package com.mainproject.server.message.dto;

import com.mainproject.server.message.entity.MessageRoom;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRoomResponseDto {

    private Long messageRoomId;

    private LocalDateTime createAt;

    private Long tutorId;

    private String tutorName;

    private Long tuteeId;

    private String tuteeName;

    private List<MessageResponseDto> messages;

    public MessageRoomResponseDto(
            MessageRoom messageRoom
    ) {
        this.messageRoomId = messageRoom.getMessageRoomId();
        this.createAt = messageRoom.getCreateAt();
        this.tutorId = messageRoom.getTutor().getProfileId();
        this.tuteeId = messageRoom.getTutee().getProfileId();
        this.tutorName = messageRoom.getTutor().getName();
        this.tuteeName = messageRoom.getTutee().getName();
        this.messages = new ArrayList<>(messageRoom.getMessages())
                .stream()
                .map(MessageResponseDto::of)
                .collect(Collectors.toList());
    }

    public static MessageRoomResponseDto of(MessageRoom messageRoom) {
        return new MessageRoomResponseDto(messageRoom);
    }
}
