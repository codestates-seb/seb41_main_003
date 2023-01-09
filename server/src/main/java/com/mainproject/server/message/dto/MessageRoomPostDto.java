package com.mainproject.server.message.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRoomPostDto {

    private Long tutorId;
    private  Long tuteeId;
}
