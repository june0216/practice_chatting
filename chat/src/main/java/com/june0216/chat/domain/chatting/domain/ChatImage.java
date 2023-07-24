package com.june0216.chat.domain.chatting.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.june0216.chat.global.entity.BaseImageEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "CHAT_IMAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatImage extends BaseImageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "picture_no")
	private Integer pictureNo;

	@Column(name = "chat_no")
	private Integer chatNo;

	@Column(name = "sort")
	private Integer sort;

}
