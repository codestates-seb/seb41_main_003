package com.mainproject.server.message;


import com.mainproject.server.constant.*;

import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.message.entity.MessageRoom;
import com.mainproject.server.message.repository.MessageRepository;
import com.mainproject.server.message.repository.MessageRoomRepository;
import com.mainproject.server.message.service.MessageService;

import com.mainproject.server.profile.entity.Profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageRoomRepository messageRoomRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    @DisplayName("메세지 룸 TEST - 아직 안됨")
    void getMessageRoom() {
        //Given
        Long userId = 1L;


        //new PageImpl<>(List<MessageRoom>messageRooms, 0,10);

        given(messageRoomRepository.findAllByTutorProfileIdOrTuteeProfileId(anyLong(),anyLong(),(any(Pageable.class)))).willReturn(Optional.ofNullable());

        //when
        Throwable throwable = catchThrowable(
                () -> messageService.getMessageRoom()
        );
        //Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.ACCESS_DENIED.getMessage());
    }


    @Test
    @DisplayName("메세지 룸 TEST - NOT FOUND EXCEPTION")
    void verifiedMessageRoom(){
        //given
        Long messageRoomId = 1L;

        given(messageRoomRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(
                () -> messageService.verifiedMessageRoom(messageRoomId)
        );
        //then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND.getMessage());
    }

    @Test
    void deleteMessageRoom() {
        //given
        MessageRoom messageRoom = new MessageRoom();
        Long messageRoomId = 1L;
        //when
        Throwable throwable = catchThrowable(
                () -> messageService.deleteMessageRoom(messageRoomId)
        );
        //then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND.getMessage());
    }
    @Test
    void 이름미정(){
        //given
        Message get = new Message();
        get.setCreateAt(LocalDateTime.now());
        get.setMessageRoom(null);

        Long profileId = 1L;
        MessageRoom messageRoom = new MessageRoom();
        Profile tutor = new Profile();
        Profile tutee = new Profile();

        //when
        Throwable throwable = catchThrowable(
                () -> messageService.sendTutoringRequestMessage(1L, messageRoom, tutor, tutee)
        );

        //then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.);

    }
}
