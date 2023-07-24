package com.june0216.chat.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.june0216.chat.domain.chatting.domain.Chat;
import com.june0216.chat.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	@Override
	Optional<Member> findById(Long id);
	Optional<Member> findByEmail(String email);
}
