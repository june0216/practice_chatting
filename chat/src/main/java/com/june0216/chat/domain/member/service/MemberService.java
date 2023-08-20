package com.june0216.chat.domain.member.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.june0216.chat.domain.member.domain.Member;
import com.june0216.chat.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;
	@Transactional(readOnly = true)
	public Member findById(Long memberId){
		return memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
	}
	public Member save(Member member){
		return memberRepository.save(member);
	}
}
