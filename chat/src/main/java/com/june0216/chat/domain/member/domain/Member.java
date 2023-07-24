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

	@Column(name = "address")
	private String address;

	private String password;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "out_yn")
	private String outYn;

	@Column(name = "denined_yn")
	private String deninedYn;

	@Column(name = "user_role")
	private String userRole;

	@Column(name = "platform")
	private String platform;

	@Column(name = "profile")
	private String profile;

	@Column(name = "reg_date")
	private LocalDateTime regDate;

	@Column(name = "nickmod_date")
	private LocalDateTime nickModDate;

	@Column(name = "passmod_date")
	private LocalDateTime passModDate;


	public void addProfileImage(String imgUrl) {
		this.profile = imgUrl;
	}

	public void changeNickname(String nickname) {
		this.nickname = nickname;
	}


}
