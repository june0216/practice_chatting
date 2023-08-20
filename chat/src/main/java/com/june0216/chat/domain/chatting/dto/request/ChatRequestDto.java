package com.june0216.chat.domain.chatting.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ChatRequestDto {

	@NotNull
	private Long createMember;

	public ChatRequestDto(Long createMember) {
		this.createMember = createMember;
	}
}
