package com.mainproject.server.auth.redis.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Setter
@RedisHash(value = "Refresh",timeToLive = 120)
public class RefreshEntity {

    @Id
    private String email;

    private String refreshToken;

    private LocalDateTime createdAt;

    public RefreshEntity(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.createdAt = LocalDateTime.now();
    }
}
