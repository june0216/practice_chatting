package com.june0216.chat.domain.chatting.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.kafka.common.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.june0216.chat.domain.chatting.domain.Message;
import com.june0216.chat.domain.chatting.dto.request.ChatRequestDto;
import com.june0216.chat.domain.chatting.dto.response.ChatRoomResponseDto;
import com.june0216.chat.domain.chatting.dto.response.ChattingHistoryResponseDto;
import com.june0216.chat.domain.chatting.service.ChatRoomService;
import com.june0216.chat.domain.chatting.service.ChatService;
import com.june0216.chat.domain.member.domain.Member;
import com.june0216.chat.domain.member.service.MemberService;
import com.june0216.chat.global.dto.StatusResponseDto;
import com.june0216.chat.global.util.AuthUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;
	private final ChatRoomService chatRoomService;
	private final MemberService memberService;

	@PostMapping("/chatrooms")
	public ResponseEntity<StatusResponseDto> createChatRoom(@RequestBody @Valid final ChatRequestDto requestDto, BindingResult bindingResult, @RequestParam Long memberId) {

		Member member = memberService.findById(memberId);

		// 채팅방을 만들어준다.
		chatService.makeChatRoom(member, requestDto);

		return ResponseEntity.ok(StatusResponseDto.addStatus(200));
	}

	// 채팅내역 조회
	@GetMapping("/chatroom/{roomNo}")
	public ResponseEntity<ChattingHistoryResponseDto> chattingList(@PathVariable("roomNo") Integer roomNo, @AuthUser Member member) {
		//Member member = memberService.findById(member);
		ChattingHistoryResponseDto chattingList = chatService.getChattingList(roomNo, member);
		return ResponseEntity.ok(chattingList);
	}

	// 채팅방 리스트 조회
	@GetMapping("/chatroom")
	public ResponseEntity<List<ChatRoomResponseDto>> chatRoomList(@RequestParam(value = "spotNo", required = false) final Long spotNo ,@AuthUser Member member) {
		List<ChatRoomResponseDto> chatList = chatService.getChatList(member, spotNo);
		return ResponseEntity.ok(chatList);
	}

	@MessageMapping("/message")
	public void sendMessage(@Valid Message message, @Header("Authorization") final String authorization) {
		chatService.sendMessage(message, authorization);
	}

	// 채팅방 접속 끊기
	@PostMapping("/chatroom/{chatroomNo}")
	public ResponseEntity<StatusResponseDto> disconnectChat(@PathVariable("chatroomNo") Integer chatroomNo,
		@RequestParam("email") String email) {

		chatRoomService.disconnectChatRoom(chatroomNo, email);
		return ResponseEntity.ok(StatusResponseDto.success());
	}


	// 메시지 전송 후 callback
	@PostMapping("/chatroom/notification")
	public ResponseEntity<Message> sendNotification(@Valid @RequestBody Message message) {
		Message savedMessage = chatService.sendNotificationAndSaveMessage(message);
		return ResponseEntity.ok(savedMessage);
	}
}
