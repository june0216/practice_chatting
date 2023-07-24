package com.june0216.chat.domain.chatting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.june0216.chat.domain.chatting.domain.Chat;
import com.june0216.chat.domain.chatting.dto.redis.ChatRoom;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, String> {

	List<ChatRoom> findByChatroomNo(Integer chatRoomNo);

	Optional<ChatRoom> findByChatroomNoAndEmail(Integer chatRoomNo, String email);
}