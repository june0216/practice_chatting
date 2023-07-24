package com.june0216.chat.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.june0216.chat.domain.chatting.domain.mongo.Chatting;

public interface MongoChatRepository extends MongoRepository<Chatting, String> {

	List<Chatting> findByChatRoomNo(Integer chatNo);

	Page<Chatting> findByChatRoomNoOrderBySendDateDesc(Integer chatRoomNo, Pageable pageable);
}
