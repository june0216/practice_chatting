package com.june0216.chat.domain.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.june0216.chat.domain.chatting.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
