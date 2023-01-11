package com.mainproject.server.auth.redis.service;

import com.mainproject.server.auth.redis.dto.RefreshDto;
import com.mainproject.server.auth.redis.entity.RefreshEntity;
import com.mainproject.server.auth.redis.repository.RefreshRepository;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefreshService {

    private final RefreshRepository refreshRepository;

    public RefreshDto createRefresh(String email, String refreshToken) {
        RefreshEntity entity = new RefreshEntity(email, refreshToken);
        return RefreshDto.of(refreshRepository.save(entity));
    }

    public RefreshDto getRefresh(String email) {
        RefreshEntity entity = refreshRepository.findById(email)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.EXPIRED_REFRESH_TOKEN));
        return RefreshDto.of(entity);
    }

    public void deleteRefresh(String email) {
        refreshRepository.deleteById(email);
    }

    public List<RefreshDto> getAll() {
        ArrayList<RefreshEntity> list = new ArrayList<>();
        refreshRepository.findAll().forEach(list::add);
        return list.stream().map(RefreshDto::of)
                .collect(Collectors.toList());
    }
}
