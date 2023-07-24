package com.june0216.chat.domain.chatting.dto.chat;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.constraints.NotNull;

import com.june0216.chat.domain.chatting.domain.mongo.Chatting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
	private String id;

	@NotNull
	private Integer chatNo;

	@NotNull
	private String contentType;

	@NotNull
	private String content;

	private String senderName;

	private Long senderNo;

	@NotNull
	private Integer saleNo;

	private long sendTime;
	private Integer readCount;
	private String senderEmail;

	public void setSendTimeAndSender(LocalDateTime sendTime, Long senderNo, String senderName, Integer readCount) {
		this.senderName = senderName;
		this.sendTime = sendTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
		this.senderNo = senderNo;
		this.readCount = readCount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Chatting convertEntity() {
		return Chatting.builder()
			.senderName(senderName)
			.senderNo(senderNo)
			.chatRoomNo(chatNo)
			.contentType(contentType)
			.content(content)
			.sendDate(Instant.ofEpochMilli(sendTime).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
			.readCount(readCount)
			.build();
	}
}
