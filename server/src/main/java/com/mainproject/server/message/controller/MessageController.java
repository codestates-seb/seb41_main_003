package com.mainproject.server.message.controller;

import com.mainproject.server.dto.PageResponseDto;
import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.message.dto.MessagePostDto;
import com.mainproject.server.message.dto.MessageRoomPostDto;
import com.mainproject.server.message.dto.MessageRoomSimpleResponseDto;
import com.mainproject.server.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity postMessage(
            @RequestBody @Validated MessagePostDto messagePostDto
    ) {
        ResponseDto response =
                ResponseDto.of(messageService.createMessage(messagePostDto));
        return new ResponseEntity<>(response ,HttpStatus.CREATED);
    }


    @PostMapping("/{profileId}")
    public ResponseEntity postMessageRoom(
            @PathVariable("profileId") Long profileId,
            @RequestBody @Validated MessageRoomPostDto messageRoomPostDto
    ) {
        //Todo 이미 튜터와 튜티 메세지 룸이 존재한다면, 에러 반환
        MessageRoomSimpleResponseDto createMessageRoom
                = messageService.createMessageRoom(messageRoomPostDto, profileId);

        return new ResponseEntity<>(
                ResponseDto.of(createMessageRoom),
                HttpStatus.CREATED
        );
    }


    @GetMapping("/{profileId}")
    public ResponseEntity getMessage(
            @PathVariable("profileId")Long profileId,
            @PageableDefault(page = 0, size = 7, sort = "messageRoomId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<MessageRoomSimpleResponseDto> messageRooms =
                messageService.getMessageRooms(profileId, pageable);
        PageResponseDto response =
                PageResponseDto.of(messageRooms.getContent(), messageRooms);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/rooms/{messageRoomId}")
    public ResponseEntity getMessages(
            @PathVariable ("messageRoomId") Long messageRoomId
    ) {
        //Todo 메세지 룸 조회시 센더인지 리시버인지 확인후 UNCHECK 상태 변화 로직 추가
        ResponseDto response =
                ResponseDto.of(messageService.getMessageRoom(messageRoomId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/rooms/{messageRoomId}")
    public ResponseEntity deleteMessages(
            @PathVariable("messageRoomId") Long messageRoomId
    ) {
        messageService.deleteMessageRoom(messageRoomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
