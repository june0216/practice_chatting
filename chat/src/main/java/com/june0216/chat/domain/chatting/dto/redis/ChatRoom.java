package com.june0216.chat.domain.chatting.dto.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RedisHash(value = "chatRoom")
public class ChatRoom {
	@Id
	private String id;

	@Indexed
	private Integer chatroomNo;

	@Indexed
	private String email;

	@Builder
	public ChatRoom(Integer chatroomNo, String email) {
		this.chatroomNo = chatroomNo;
		this.email = email;
	}
}
