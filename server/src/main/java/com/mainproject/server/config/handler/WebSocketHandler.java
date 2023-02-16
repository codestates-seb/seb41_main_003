package com.mainproject.server.config.handler;


import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler implements ChannelInterceptor {
    private final JwtTokenizer jwtTokenizer;

    // WebSocket 요청 필터 역활
    @Override
    @CrossOrigin
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            String jwt = Optional.of(accessor.getFirstNativeHeader("Authorization")
                    .substring("Bearer ".length()))
                    .orElseThrow(() -> new ServiceLogicException(ErrorCode.ACCESS_DENIED));
            jwtTokenizer.verifyAccessToken(jwt);
        }
        return message;
    }
}
