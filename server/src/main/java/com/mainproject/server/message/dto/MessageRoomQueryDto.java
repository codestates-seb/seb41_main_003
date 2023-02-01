package com.mainproject.server.message.dto;

import com.mainproject.server.constant.MessageStatus;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.profile.entity.Profile;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRoomQueryDto {

    private Long messageRoomId;

    private MessageStatus messageStatus;

    private Long tutoringId;

    private Profile tutor;

    private Profile tutee;

    private String tutorProfileImageUrl;

    private String tuteeProfileImageUrl;

    private String lastMessage;

    private Long lastSenderId;

    private ProfileStatus currentProfileStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
