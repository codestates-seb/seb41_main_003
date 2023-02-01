package com.mainproject.server.message.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.MessageStatus;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.message.dto.*;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.message.entity.MessageRoom;
import com.mainproject.server.message.repository.MessageRepository;
import com.mainproject.server.message.repository.MessageRoomRepository;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.repository.TutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        Message message = saveMessage(postDto, messageRoom, sender, receiver);
        return MessageResponseDto.of(messageRepository.save(message));
    }

    public MessageRoomSimpleResponseDto createMessageRoom(
            MessageRoomPostDto postDto,
            Long profileId
    ) {
        Profile profile = profileService.verifiedProfileById(profileId);
        Profile tutor = profileService.verifiedProfileById(postDto.getTutorId());
        Profile tutee = profileService.verifiedProfileById(postDto.getTuteeId());
        if (tutor.getProfileStatus().equals(ProfileStatus.TUTOR) &&
                tutee.getProfileStatus().equals(ProfileStatus.TUTEE)) {
            verifyMessageRoom(tutor, tutee);
            MessageRoom save = saveMessageRoom(tutor, tutee);
            return MessageRoomSimpleResponseDto.of(profile.getProfileStatus(), save);
        } else {
            throw new ServiceLogicException(ErrorCode.WRONG_TUTOR_AND_TUTEE);
        }
    }

    public Page<MessageRoomSimpleResponseDto> getMessageRooms(
            Long profileId,
            Pageable pageable
    ) {
        Page<MessageRoomQueryDto> messageRooms =
                messageRoomRepository.findQueryMessageRoom(
                        profileId, pageable
                );
        List<MessageRoomQueryDto> messageRoomList = messageRooms.getContent();
        List<MessageRoomSimpleResponseDto> simpleResponseDtoList =
                messageRoomList.stream()
                        .map(MessageRoomSimpleResponseDto::of)
                        .collect(Collectors.toList());

        return new PageImpl<>(
                simpleResponseDtoList,
                messageRooms.getPageable(),
                messageRooms.getTotalElements()
        );

    }

    public MessageRoomResponseDto getMessageRoom(Long messageRoomId, Long profileId) {
        MessageRoom findMessageRoom = verifiedMessageRoom(messageRoomId);
        if (!findMessageRoom.getTutor().getProfileId().equals(profileId) &&
                !findMessageRoom.getTutee().getProfileId().equals(profileId))
            throw new ServiceLogicException(ErrorCode.ACCESS_DENIED);
        ArrayList<Message> messages = new ArrayList<>(findMessageRoom.getMessages());
        if (!messages.isEmpty()) {
            Message message = messages.get(messages.size() - 1);
            if (profileId.equals(message.getReceiver().getProfileId())) {
                findMessageRoom.setMessageStatus(MessageStatus.CHECK);
            }
        }
        return MessageRoomResponseDto.of(messageRoomRepository.save(findMessageRoom));
    }

    public MessageRoom updateMessageRoom(Long messageRoomId, Long tutoringId) {
        MessageRoom messageRoom = verifiedMessageRoom(messageRoomId);
        if (messageRoom.getTutoringId() == null) {
            messageRoom.setTutoringId(tutoringId);
        } else {
            throw new ServiceLogicException(ErrorCode.MATCHING_REQUEST_EXISTS);
        }
        return messageRoomRepository.save(messageRoom);
    }

    public void deleteMessageRoom(Long messageRoomId) {
        MessageRoom messageRoom = verifiedMessageRoom(messageRoomId);
        if (messageRoom.getTutoringId() == null) {
            messageRepository.deleteAllById(messageRoom.getMessages()
                    .stream().map(Message::getMessageId)
                    .collect(Collectors.toList()));
            messageRoomRepository.deleteById(messageRoomId);
        } else {
            throw new ServiceLogicException(ErrorCode.PROGRESS_TUTORING_BAD_REQUEST);
        }

    }

    public void sendTutoringRequestMessage(
            Long profileId,
            MessageRoom messageRoom,
            Profile tutor,
            Profile tutee
    ) {
        if (Objects.equals(profileId, tutor.getProfileId())) {
            delegateSendMessage(messageRoom, tutor, tutee);
        } else {
            delegateSendMessage(messageRoom, tutee, tutor);
        }
    }

    private void delegateSendMessage(MessageRoom messageRoom, Profile sender, Profile receiver) {
        Message sendMessage = new Message();
        sendMessage.addSender(sender);
        sendMessage.addReceiver(receiver);
        sendMessage.setMessageContent("REQ_UEST");
        Message saveSendMessage = messageRepository.save(sendMessage);
        saveSendMessage.addMessageRoom(messageRoom);
        messageRoomRepository.save(messageRoom);
    }

    /* 검증 및 유틸 로직 */

    public void deleteMessageRoomTutoringId(Long tutoringId) {
        Optional<MessageRoom> findMessageRoom =
                messageRoomRepository.findByTutoringId(tutoringId);
        if (findMessageRoom.isPresent()) {
            findMessageRoom.get().setTutoringId(null);
            messageRoomRepository.save(findMessageRoom.get());
        }
    }

    public MessageRoom verifiedMessageRoom(Long messageRoomId) {
        return messageRoomRepository.findById(messageRoomId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));
    }

    private void verifyMessageRoom(Profile tutor, Profile tutee) {
        if (messageRoomRepository
                .findByTutorProfileIdAndTuteeProfileId(
                        tutor.getProfileId(),
                        tutee.getProfileId()).isPresent()
        ) {
            throw new ServiceLogicException(ErrorCode.MESSAGE_ROOM_EXISTS);
        }
    }

    private static Message saveMessage(MessagePostDto postDto, MessageRoom messageRoom, Profile sender, Profile receiver) {
        Message message = new Message();
        message.setMessageContent(postDto.getMessageContent());
        message.addSender(sender);
        message.addReceiver(receiver);
        message.addMessageRoom(messageRoom);
        return message;
    }

    private MessageRoom saveMessageRoom(Profile tutor, Profile tutee) {
        MessageRoom messageRoom = new MessageRoom();
        messageRoom.setMessageStatus(MessageStatus.UNCHECK);
        messageRoom.addTutor(tutor);
        messageRoom.addTutee(tutee);
        return messageRoomRepository.save(messageRoom);
    }

}


