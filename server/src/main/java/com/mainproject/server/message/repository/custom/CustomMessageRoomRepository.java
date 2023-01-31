package com.mainproject.server.message.repository.custom;

import com.mainproject.server.message.dto.MessageRoomQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomMessageRoomRepository {
    Page<MessageRoomQueryDto> findQueryMessageRoom(
            Long profileId,
            Pageable pageable
    );


}
