package com.june0216.chat.domain.chatting.domain.mongo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "chatting")
@Getter
@ToString
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Chatting { //MongoDB에서 메시지 저장에 사용할 도메인 모델
	@Id
	private String id;
	private Integer chatRoomNo;
	private Long senderNo;
	private String senderName;
	private String contentType;
	private String content;
	private LocalDateTime sendDate;
	private long readCount;
}
