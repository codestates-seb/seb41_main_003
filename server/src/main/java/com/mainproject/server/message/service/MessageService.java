package com.mainproject.server.message.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.MessageStatus;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.message.entity.MessageRoom;
import com.mainproject.server.message.repository.MessageRepository;
import com.mainproject.server.message.repository.MessageRoomRepository;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;
    private final ProfileRepository profileRepository;

    public Message createMessage(Message message, Long profileId){

        message.addTuteeProfile(new Profile());
        message.addTutorProfile(new Profile());

        messageRepository.save(message);

        return message;
    }

    public MessageRoom createMessageRoom(MessageRoom messageRoom, Message message, Long profileId) {

        messageRoom.addMessage(message);
        messageRoom.setMessageStatus(MessageStatus.UNCHECK);

        return messageRoom;
    }

    public MessageRoom findVerifiedMessageRoom(Long messageRoomId) {

        Optional<MessageRoom> optionalMessageRoom = messageRoomRepository.findById(messageRoomId);
        MessageRoom messageRoom = optionalMessageRoom.orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));

        return null;
    }

    Page<MessageRoom> getAllTutoring(Long profileId, Pageable pageable) {
        Page<MessageRoom> messageRooms =
                messageRoomRepository.findAllByTutorProfileIdAndTuteeProfileId(profileId, profileId, pageable);

        return messageRooms;
    }

}


