package com.june0216.chat.domain.member.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "MEMBER")
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
@ToString
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "email")
	private String email;


	private String password;

	@Column(name = "nickname")
	private String nickname;


	@Column(name = "role_user")
	private String roleUser;


	@Column(name = "profile")
	private String profile;

	@Builder
	public Member(String email, String password, String nickname, String profile) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.profile = profile;
	}

	public void addProfileImage(String imgUrl) {
		this.profile = imgUrl;
	}

	public void changeNickname(String nickname) {
		this.nickname = nickname;
	}


}
