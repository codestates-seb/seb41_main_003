package com.mainproject.server.message.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainproject.server.alarm.entity.Alarm;
import com.mainproject.server.message.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            //레디스 에서 받은 데이터를 역직렬화
            String publishMessage = String.valueOf(
                    redisTemplate
                            .getStringSerializer()
                            .deserialize(message.getBody()));
            if (publishMessage.contains("alarmId")) {
                Alarm redisAlarm = objectMapper.readValue(publishMessage, Alarm.class);
//                messagingTemplate.convertAndSend("/sub/alarm/" + redisAlarm.getMemberId(), redisAlarm);
                log.info("레디스에서 Alarm 받아옴");
            } else {
                MessageResponseDto dto = objectMapper.readValue(publishMessage, MessageResponseDto.class);
                log.info("message = {}", dto);
                messagingTemplate.convertAndSend("/sub/room/" + dto.getMessageRoomId(), dto);
                log.info("레디스에서 메세지 받아옴");
            }

        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
