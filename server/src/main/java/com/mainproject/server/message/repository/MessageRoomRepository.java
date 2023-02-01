package com.mainproject.server.message.repository;

import com.mainproject.server.message.entity.MessageRoom;
import com.mainproject.server.message.repository.custom.CustomMessageRoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long>,
        CustomMessageRoomRepository
{
    Page<MessageRoom> findAllByTutorProfileIdOrTuteeProfileId(Long profileId, Long profileId1, Pageable pageable);

    Optional<MessageRoom> findByTutorProfileIdAndTuteeProfileId(Long tutorId, Long tuteeId);

    Optional<MessageRoom> findByTutoringId(Long tutoringId);
}
