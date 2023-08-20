package com.june0216.chat.domain.member.dto;

import com.june0216.chat.domain.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberReqDto {
	private String email;
	private String password;
	private String nickname;
	private String profile;

	@Builder
	public MemberReqDto(String email, String password, String profile, String nickname) {
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.nickname = nickname;
	}
	public Member toEntity(){
		return Member.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.profile(profile)
			.build();
	}
}
