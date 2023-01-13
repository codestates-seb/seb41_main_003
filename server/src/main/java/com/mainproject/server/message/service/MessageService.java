package com.mainproject.server.message.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.MessageStatus;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.message.dto.*;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.message.entity.MessageRoom;
import com.mainproject.server.message.repository.MessageRepository;
import com.mainproject.server.message.repository.MessageRoomRepository;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.tutoring.repository.TutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final MessageRoomRepository messageRoomRepository;

    private final ProfileService profileService;

    private final TutoringRepository tutoringRepository;


    public MessageResponseDto createMessage(
            MessagePostDto postDto
    ) {
        MessageRoom messageRoom = verifiedMessageRoom(postDto.getMessageRoomId());
        Profile sender = profileService.verifiedProfileById(postDto.getSenderId());
        Profile receiver = profileService.verifiedProfileById(postDto.getReceiverId());
        Message message = new Message();
        message.setMessageContent(postDto.getMessageContent());
        message.addSender(sender);
        message.addReceiver(receiver);
        message.addMessageRoom(messageRoom);
        return MessageResponseDto.of(messageRepository.save(message));
    }

    public MessageRoomSimpleResponseDto createMessageRoom(
            MessageRoomPostDto postDto,
            Long profileId
    ) {
        Profile profile = profileService.verifiedProfileById(profileId);
        Profile tutor = profileService.verifiedProfileById(postDto.getTutorId());
        Profile tutee = profileService.verifiedProfileById(postDto.getTuteeId());
        MessageRoom messageRoom = new MessageRoom();
        messageRoom.setMessageStatus(MessageStatus.UNCHECK);
        messageRoom.addTutor(tutor);
        messageRoom.addTutee(tutee);
        MessageRoom save = messageRoomRepository.save(messageRoom);
        return MessageRoomSimpleResponseDto.of(profile.getProfileStatus(), save);
    }

    public Page<MessageRoomSimpleResponseDto> getMessageRooms(
            Long profileId,
            Pageable pageable
    ) {
        Profile profile = profileService.verifiedProfileById(profileId);
        ProfileStatus profileStatus = profile.getProfileStatus();
        Page<MessageRoom> messageRooms =
                messageRoomRepository.findAllByTutorProfileIdOrTuteeProfileId(
                        profileId, profileId, pageable
                );
        List<MessageRoom> messageRoomList = messageRooms.getContent();
        List<MessageRoomSimpleResponseDto> simpleResponseDtoList =
                messageRoomList.stream()
                .map(mr -> MessageRoomSimpleResponseDto.of(profileStatus, mr))
                .collect(Collectors.toList());

        return new PageImpl<>(
                simpleResponseDtoList,
                messageRooms.getPageable(),
                messageRooms.getTotalElements()
        );

    }

    public MessageRoomResponseDto getMessageRoom(Long messageRoomId) {
        MessageRoom findMessageRoom = verifiedMessageRoom(messageRoomId);
        findMessageRoom.setMessageStatus(MessageStatus.CHECK);
        return MessageRoomResponseDto.of(messageRoomRepository.save(findMessageRoom));
    }

    public void updateMessageRoom(Long messageRoomId, Long tutoringId) {
        MessageRoom messageRoom = verifiedMessageRoom(messageRoomId);
        messageRoom.setTutoringId(tutoringId);

        messageRoomRepository.save(messageRoom);
    }

    public void deleteMessageRoom(Long messageRoomId) {
        MessageRoom messageRoom = verifiedMessageRoom(messageRoomId);

        if (messageRoom.getTutoringId() != null) {
            tutoringRepository.deleteById(messageRoom.getTutoringId());
        }

        messageRoomRepository.delete(messageRoom);
    }

    /* 검증 로직 */
    public MessageRoom verifiedMessageRoom(Long messageRoomId) {
        return messageRoomRepository.findById(messageRoomId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));
    }
}


