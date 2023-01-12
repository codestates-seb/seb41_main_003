package com.mainproject.server.message.controller;

import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.message.dto.MessagePostDto;
import com.mainproject.server.message.dto.MessageResponseDto;
import com.mainproject.server.message.dto.MessageRoomPostDto;
import com.mainproject.server.message.dto.MessageRoomResponseDto;
import com.mainproject.server.utils.StubData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final StubData stubData;


    @PostMapping
    public ResponseEntity postMessage(
            @RequestBody @Validated MessageResponseDto messageResponseDto
    ) {

        return new ResponseEntity<>(
                ResponseDto.of(stubData.createMessageResponse()),
                HttpStatus.CREATED);

    }

    @PostMapping("/{profileId}")
    public ResponseEntity postMessageRoom(
            @PathVariable("profileId") Long profileId,
            @RequestBody @Validated MessageRoomPostDto messageRoomPostDto
    ) {


        return new ResponseEntity<>(
                ResponseDto.of(stubData.createMessageRoomResponse()),
                HttpStatus.CREATED);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity getMessage(
            @PathVariable("profileId")Long profileId
    ) {

        MessageResponseDto response = stubData.createMessageResponse();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rooms/{messageRoomId}")
    public ResponseEntity getMessages(@PathVariable ("messageRoomId") Long profileId) {

        MessageRoomResponseDto response = stubData.createMessageRoomResponse();


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/rooms/{messageRoomId}")
    public ResponseEntity deleteMessages(@PathVariable("messageRoomId") Long profileId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
