package com.mainproject.server.message.ui;

import com.mainproject.server.alarm.entity.Alarm;
import com.mainproject.server.message.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, MessageResponseDto message){
        redisTemplate.convertAndSend(topic.getTopic(), message);
        log.info("레디스로 메세지 보냄");
    }

    public void publish(ChannelTopic topic, Alarm alarm){
        redisTemplate.convertAndSend(topic.getTopic(), alarm);
        log.info("레디스로 알림 보냄");
    }
}