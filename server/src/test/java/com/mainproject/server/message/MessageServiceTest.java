package com.mainproject.server.message;


import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.MessageStatus;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.image.entity.ProfileImage;
import com.mainproject.server.message.dto.MessageRoomPostDto;
import com.mainproject.server.message.dto.MessageRoomQueryDto;
import com.mainproject.server.message.dto.MessageRoomSimpleResponseDto;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.message.entity.MessageRoom;
import com.mainproject.server.message.repository.MessageRepository;
import com.mainproject.server.message.repository.MessageRoomRepository;
import com.mainproject.server.message.service.MessageService;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.tutoring.repository.TutoringRepository;
import com.mainproject.server.utils.ResponseStubData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageRoomRepository messageRoomRepository;

    @Mock
    private ProfileService profileService;

    @Mock
    private TutoringRepository tutoringRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    @DisplayName("메세지 룸 상세 조회 TEST - ACCESS DENIED EXCEPTION")
    void givenMessageRoomWhenThrowExceptionThenEqualsServiceLogicException() {
        //Given
        Long profileId = 3L;
        Long messageRoomId = 1L;
        MessageRoom messageRoom = MessageRoom.builder()
                .messageRoomId(1L)
                .tutor(Profile.builder().profileId(1L).build())
                .tutee(Profile.builder().profileId(2L).build())
                .build();
        given(messageRoomRepository.findById(anyLong()))
                .willReturn(Optional.of(messageRoom));
        //when
        Throwable throwable = catchThrowable(
                () -> messageService.getMessageRoom(messageRoomId, profileId)
        );
        //Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.ACCESS_DENIED.getMessage());
    }


    @Test
    @DisplayName("메세지 룸 검증 메소드 TEST - NOT FOUND EXCEPTION")
    void givenNullWhenThrowExceptionThenEqualsServiceLogicException(){
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
    @DisplayName("메세지 룸 삭제 TEST - NOT FOUND EXCEPTION")
    void givenNullWhenDeleteThrowExceptionThenEqualsServiceLogicException() {
        //given
        MessageRoom messageRoom = new MessageRoom();
        Long messageRoomId = 1L;
        given(messageRoomRepository.findById(anyLong()))
                .willReturn(Optional.empty());
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
    @DisplayName("메세지 룸 생성 TEST - 튜터/튜티 유효성 검증 예외 발생")
    void givenAllTuteeWhenThrowExceptionThenEqualsServiceLogicExcepiton() {
        // Given
        Long profileId = 1L;
        MessageRoomPostDto dto = new MessageRoomPostDto();
        dto.setTuteeId(1L);
        dto.setTutorId(1L);
        given(profileService.verifiedProfileById(anyLong()))
                .willReturn(Profile.builder().profileStatus(ProfileStatus.TUTEE).build());
        // When
        Throwable throwable = catchThrowable(
                () -> messageService.createMessageRoom(dto, profileId)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.WRONG_TUTOR_AND_TUTEE.getMessage());
    }


    @Test
    @DisplayName("메세지 룸 리스트 조회 TEST")
    void givenTestMessageRoomWhenGetMessageRoomThenEqualsData() {
        // Given
        Long profileId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Profile profile = Profile
                .builder()
                .profileStatus(ProfileStatus.TUTEE).build();
        MessageRoomQueryDto testMessageRoom = ResponseStubData.createMessageRoomQueryDto();
        Page<MessageRoomQueryDto> page = new PageImpl<>(
                List.of(testMessageRoom, testMessageRoom),
                pageable, 2);

        given(messageRoomRepository.findQueryMessageRoom(
                anyLong(), any(Pageable.class)
        )).willReturn(page);
        // When
        Page<MessageRoomSimpleResponseDto> messageRooms = messageService.getMessageRooms(profileId, pageable);
        MessageRoomSimpleResponseDto response = messageRooms.getContent().get(0);
        // Then
        assertThat(response.getMessageStatus()).isEqualTo(MessageStatus.CHECK.name());
        assertThat(response.getLastMessage()).isEqualTo("test");
        assertThat(response.getLastSenderId()).isEqualTo(1L);
        assertThat(response.getTargetName()).isEqualTo("test");
        assertThat(response.getTargetProfileUrl()).isEqualTo("testUrl");
        assertThat(messageRooms.getNumber()).isEqualTo(0);
        assertThat(messageRooms.getSize()).isEqualTo(10);
        assertThat(messageRooms.getTotalElements()).isEqualTo(2);
    }

}
