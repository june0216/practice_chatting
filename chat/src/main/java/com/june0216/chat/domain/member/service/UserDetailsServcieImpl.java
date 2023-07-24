package com.june0216.chat.domain.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.june0216.chat.domain.member.domain.Member;
import com.june0216.chat.domain.member.domain.MemberAdapter;
import com.june0216.chat.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServcieImpl implements UserDetailsService {
	private final MemberRepository
		memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);
		Member member = memberRepository.findByEmail(email).orElseThrow(
			RuntimeException::new);

		// UserDetails를 반환한다.
		return new MemberAdapter(member);
	}

}
