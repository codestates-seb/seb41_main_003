package com.mainproject.server.profile.repository.custom;


import com.mainproject.server.profile.dto.ProfileQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProfileRepository {
    Page<ProfileQueryDto> findQueryProfile(
            String key,
            String subject,
            String name,
            Pageable pageable
    );
}
