package com.june0216.chat.domain.member.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.june0216.chat.domain.member.domain.Member;
import com.june0216.chat.domain.member.dto.MemberReqDto;
import com.june0216.chat.domain.member.service.MemberService;
import com.june0216.chat.global.dto.StatusResponseDto;
import com.june0216.chat.global.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final JwtProvider jwtProvider;

	@PostMapping("/member")
	public ResponseEntity<String> register(@RequestBody MemberReqDto memberReqDto) {

		Member member = memberReqDto.toEntity();
		// 회원을 저장한다.
		memberService.save(member);
		String accessToken = jwtProvider.generateAccessToken(member.getEmail());
		return ResponseEntity.ok(accessToken);
	}

}
