package com.mainproject.server.auth.redis.dto;

import com.mainproject.server.auth.redis.entity.RefreshEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RefreshDto {

    private String email;

    private String refreshToken;

    private LocalDateTime createdAt;

    public RefreshDto(RefreshEntity entity) {
        this.email = entity.getEmail();
        this.refreshToken = entity.getRefreshToken();
        this.createdAt = entity.getCreatedAt();
    }

    public static RefreshDto of(RefreshEntity entity) {
        return new RefreshDto(entity);
    }
}
