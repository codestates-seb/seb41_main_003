package com.mainproject.server.auth.redis.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Setter
// 1달 43200 sec
@RedisHash(value = "Refresh", timeToLive = 43200)
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
