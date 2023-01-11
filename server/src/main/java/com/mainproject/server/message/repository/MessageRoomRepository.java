package com.mainproject.server.message.repository;

import com.mainproject.server.message.entity.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
}
