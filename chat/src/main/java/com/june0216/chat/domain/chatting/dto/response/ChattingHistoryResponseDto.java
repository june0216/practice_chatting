package com.june0216.chat.domain.chatting.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ChattingHistoryResponseDto {
	private String email;
	private List<ChatResponseDto> chatList;
}
