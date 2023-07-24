package com.june0216.chat.domain.chatting.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.june0216.chat.domain.MongoChatRepository;
import com.june0216.chat.domain.chatting.domain.AggregationSender;
import com.june0216.chat.domain.chatting.domain.Chat;
import com.june0216.chat.domain.chatting.domain.mongo.Chatting;
import com.june0216.chat.domain.chatting.domain.Message;
import com.june0216.chat.domain.chatting.dto.aggregation.AggregationDto;
import com.june0216.chat.domain.chatting.dto.aggregation.AggregationTarget;
import com.june0216.chat.domain.chatting.dto.request.ChatRequestDto;
import com.june0216.chat.domain.chatting.dto.response.ChatResponseDto;
import com.june0216.chat.domain.chatting.dto.response.ChatRoomResponseDto;
import com.june0216.chat.domain.chatting.dto.response.ChattingHistoryResponseDto;
import com.june0216.chat.domain.chatting.repository.ChatRepository;
import com.june0216.chat.domain.member.domain.Member;
import com.june0216.chat.domain.member.repository.MemberRepository;
import com.june0216.chat.global.util.ConstantUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {
	private final MongoTemplate mongoTemplate;
	private final MongoChatRepository mongoChatRepository;
	private final ChatRepository chatRepository;
	private final ChatQueryService chatQueryService;

	private final MessageSender sender;
	private final AggregationSender aggregationSender;
	private final MemberRepository memberRepository;

	private final ChatRoomService chatRoomService;
	//private final NotificationService notificationService;

	@Transactional
	public Chat makeChatRoom(Member member, ChatRequestDto requestDto) {

		Chat chat = Chat.builder()
			.spotNo(requestDto.getSpotNo())
			.createMember(requestDto.getCreateMember())
			.joinMember(member.getMemberId())
			.regDate(LocalDateTime.now())
			.build();

		Chat savedChat = chatRepository.save(chat);

		// 채팅방 카운트 증가
		AggregationDto aggregationDto = AggregationDto
			.builder()
			.isIncrease("true")
			.target(AggregationTarget.CHAT)
			.spotNo(requestDto.getSpotNo())
			.build();

		aggregationSender.send(ConstantUtil.KAFKA_AGGREGATION, aggregationDto);
		return savedChat;
	}

	public List<ChatRoomResponseDto> getChatList(Member member, Long spotNo) {
		List<ChatRoomResponseDto> chatRoomList = chatQueryService.getChattingList(member.getMemberId(), spotNo);

		chatRoomList
			.forEach(chatRoomDto -> {
				// 채팅방별로 읽지 않은 메시지 개수를 셋팅
				long unReadCount = countUnReadMessages(chatRoomDto.getChatNo(), member.getMemberId());
				chatRoomDto.setUnReadCount(unReadCount);

				// 채팅방별로 마지막 채팅내용과 시간을 셋팅
				Page<Chatting> chatting =
					mongoChatRepository.findByChatRoomNoOrderBySendDateDesc(chatRoomDto.getChatNo(), PageRequest.of(0, 1));
				if (chatting.hasContent()) {
					Chatting chat = chatting.getContent().get(0);
					ChatRoomResponseDto.LatestMessage latestMessage = ChatRoomResponseDto.LatestMessage.builder()
						.context(chat.getContent())
						.sendAt(chat.getSendDate())
						.build();
					chatRoomDto.setLatestMessage(latestMessage);
				}
			});

		return chatRoomList;
	}

	public ChattingHistoryResponseDto getChattingList(Integer chatRoomNo, Member member) {
		updateCountAllZero(chatRoomNo, member.getEmail());
		List<ChatResponseDto> chattingList = mongoChatRepository.findByChatRoomNo(chatRoomNo)
			.stream()
			.map(chat -> new ChatResponseDto(chat, member.getMemberId()))
			.collect(Collectors.toList());

		return ChattingHistoryResponseDto.builder()
			.chatList(chattingList)
			.email(member.getEmail())
			.build();
	}


	public void sendMessage(Message message, Long memberId) {
		// 메시지 전송 요청 헤더에 포함된 Access Token에서 email로 회원을 조회한다.
		Member findMember = memberRepository.findById(memberId)
			.orElseThrow(IllegalStateException::new);

		// 채팅방에 모든 유저가 참여중인지 확인한다.
		boolean isConnectedAll = chatRoomService.isAllConnected(message.getChatNo());
		// 1:1 채팅이므로 2명 접속시 readCount 0, 한명 접속시 1
		Integer readCount = isConnectedAll ? 0 : 1;
		// message 객체에 보낸시간, 보낸사람 memberNo, 닉네임을 셋팅해준다.
		message.setSendTimeAndSender(LocalDateTime.now(), findMember.getMemberId(), findMember.getNickname(), readCount);
		// 메시지를 전송한다.
		sender.send(ConstantUtil.KAFKA_TOPIC, message);
	}

	@Transactional
	public Message sendNotificationAndSaveMessage(Message message) {
		// 메시지 저장과 알람 발송을 위해 메시지를 보낸 회원을 조회
		Member findMember = memberRepository.findById(message.getSenderNo())
			.orElseThrow(IllegalStateException::new);

		// 상대방이 읽지 않은 경우에만 알림 전송
		if (message.getReadCount().equals(1)) {
			// 알람 전송을 위해 메시지를 받는 사람을 조회한다.
			Member receiveMember = chatQueryService.getReceiverNumber(message.getChatNo(), message.getSenderNo());
			String content =
				message.getContentType().equals("image") ? "image" : message.getContent();
			// 알람을 보낼 URL을 생성한다.
			String sendUrl = getNotificationUrl(message.getSpotNo(), message.getChatNo());

			// 알림을 전송한다.

		}

		// 보낸 사람일 경우에만 메시지를 저장 -> 중복 저장 방지
		if (message.getSenderEmail().equals(findMember.getEmail())) {
			// Message 객체를 채팅 엔티티로 변환한다.
			Chatting chatting = message.convertEntity();
			// 채팅 내용을 저장한다.
			Chatting savedChat = mongoChatRepository.save(chatting);
			// 저장된 고유 ID를 반환한다.
			message.setId(savedChat.getId());
		}

		return message;
	}

	public void updateMessage(String email, Integer chatRoomNo) {
		Message message = Message.builder()
			.contentType("notice")
			.chatNo(chatRoomNo)
			.content(email + " 님이 돌아오셨습니다.")
			.build();

		sender.send(ConstantUtil.KAFKA_TOPIC, message);
	}


	// 읽지 않은 메시지 채팅장 입장시 읽음 처리
	public void updateCountAllZero(Integer chatNo, String email) {
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(IllegalStateException::new);

		Update update = new Update().set("readCount", 0);
		Query query = new Query(Criteria.where("chatRoomNo").is(chatNo)
			.and("senderNo").ne(findMember.getMemberId()));

		mongoTemplate.updateMulti(query, update, Chatting.class);
	}

	// 읽지 않은 메시지 카운트
	long countUnReadMessages(Integer chatRoomNo, Long senderNo) {
		Query query = new Query(Criteria.where("chatRoomNo").is(chatRoomNo)
			.and("readCount").is(1)
			.and("senderNo").ne(senderNo));

		return mongoTemplate.count(query, Chatting.class);
	}

	private String getNotificationUrl(Long saleNo, Integer chatNo) {
		return chatNo +
			"?adoptId=" +
			saleNo;
	}
}
