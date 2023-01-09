package com.mainproject.server.message.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRoomResponseDto {

    private Long messageRoomId;

    private String targetName;  //메세지 상대 이름 ex) 내가 튜티면 targetName은 튜터 이름

    private List<MessageResponseDto> messages;    //전체메세지 리스트

    private LocalDateTime createAt;

    private Long tutorId;

    private String tutorName;

    private Long tuteeId;

    private String tuteeName;

}
