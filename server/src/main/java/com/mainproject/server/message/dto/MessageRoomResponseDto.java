package com.mainproject.server.message.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRoomResponseDto {
    //메세지 방 ID , 메세지 상대 이름, 전체 메세지(messageResponseDtoList), 생성시간

    private Long messageRoomId;

    private String targetName;  //메세지 상대 이름 ex) 내가 튜티면 targetName은 튜터 이름

    private List<MessageRoomResponseDto> messageResponseDtoList;    //전체메세지 리스트

    private LocalDateTime createAt;


}
