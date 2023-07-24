package com.june0216.chat.domain.chatting.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ChatRequestDto {

	@NotNull
	private Long spotNo;
	@NotNull
	private Long createMember;
}
