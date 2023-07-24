package com.june0216.chat.domain.chatting.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.june0216.chat.domain.chatting.domain.Chat;
import com.june0216.chat.domain.chatting.dto.response.ChatRoomResponseDto;
import com.june0216.chat.domain.member.repository.MemberRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.june0216.chat.domain.chatting.domain.QChat.*;
import static com.june0216.chat.domain.member.domain.QMember.*;
import static com.june0216.chat.domain.spot.QSpot.*;

import com.june0216.chat.domain.chatting.domain.Chat;
import com.june0216.chat.domain.chatting.dto.response.ChatRoomResponseDto;
import com.june0216.chat.domain.member.domain.Member;
import com.june0216.chat.domain.member.repository.MemberRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatQueryService {

	private final JPAQueryFactory jpaQueryFactory;
	private final MemberRepository memberRepository;

	// 채팅방 리스트 조회
	public List<ChatRoomResponseDto> getChattingList(Long memberId, Long spotNo) {
		return jpaQueryFactory.select(Projections.constructor(ChatRoomResponseDto.class,
				chat.chatNo,
				chat.createMember,
				chat.joinMember,
				chat.spotNo,
				chat.regDate,
				Projections.constructor(ChatRoomResponseDto.Participant.class,
					ExpressionUtils.as(
						JPAExpressions.select(member.nickname)
							.from(member)
							.where(member.memberId.eq(
								new CaseBuilder()
									.when(chat.createMember.eq(memberId)).then(chat.joinMember)
									.otherwise(chat.createMember)

							))
						, "nickname"),
					ExpressionUtils.as(
						JPAExpressions.select(member.profile)
							.from(member)
							.where(member.memberId.eq(
								new CaseBuilder()
									.when(chat.createMember.eq(memberId)).then(chat.joinMember)
									.otherwise(chat.createMember)
							)), "profile"))
			))
			.from(chat)
			.join(spot).on(spot.spotNo.eq(chat.spotNo))
			.where(chat.createMember.eq(memberId).or(chat.joinMember.eq(memberId)), spotNoEq(spotNo))
			.fetch();
	}

	// 현재 메시지를 받는 사람을 조회하는 메서드
	public Member getReceiverNumber(Integer chatNo, Long senderNo) {
		Chat chatroom = jpaQueryFactory.select(chat)
			.from(chat)
			.where(chat.chatNo.eq(chatNo))
			.fetchOne();

		Long memberId = chatroom.getCreateMember().equals(senderNo) ?
			chatroom.getJoinMember() : chatroom.getCreateMember();

		return memberRepository.findById(memberId)
			.orElseThrow(IllegalStateException::new);
	}

	private BooleanExpression spotNoEq(Long spotNo) {
		return Objects.nonNull(spotNo) ? chat.spotNo.eq(spotNo) : null;
	}

}