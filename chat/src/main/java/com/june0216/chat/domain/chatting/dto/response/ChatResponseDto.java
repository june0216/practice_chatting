package com.june0216.chat.domain.chatting.dto.response;

import java.time.ZoneId;

import com.june0216.chat.domain.chatting.domain.mongo.Chatting;

public class ChatResponseDto {
	private String id;
	private Integer chatRoomNo;
	private Long senderNo;
	private String senderName;
	private String contentType;
	private String content;
	private long sendDate;
	private long readCount;
	private boolean isMine;

	public ChatResponseDto(Chatting chatting, Long memberNo) {
		this.id = chatting.getId();
		this.chatRoomNo = chatting.getChatRoomNo();
		this.senderNo = chatting.getSenderNo();
		this.senderName = chatting.getSenderName();
		this.contentType = chatting.getContentType();
		this.content = chatting.getContent();
		this.sendDate = chatting.getSendDate().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
		this.readCount = chatting.getReadCount();
		this.isMine = chatting.getSenderNo().equals(memberNo);
	}
}
