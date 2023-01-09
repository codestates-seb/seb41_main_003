package com.mainproject.server.message.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRoomSimpleResponseDto {
    // 메세지 방 ID, 메세지 상대 이름, 최근 메세지(messageResponseDto), 읽음 상태, 생성 시간
    private Long messageRoomId;

    private String MessageStatus;

    private String lastMessage;

    private String targetName; //메세지 상대 이름 ex) 내가 튜티면 targetName은 튜터 이름

    private LocalDateTime createAt;

}
