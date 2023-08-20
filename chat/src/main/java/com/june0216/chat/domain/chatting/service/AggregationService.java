package com.june0216.chat.domain.chatting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.june0216.chat.domain.chatting.dto.aggregation.AggregationDto;
import com.june0216.chat.domain.chatting.repository.AggregationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AggregationService {
	private final AggregationRepository aggregationRepository;

/*
	@Transactional
	public void aggregation(AggregationDto aggregationDto) {
		switch (aggregationDto.getTarget()) {
			case CHAT:
				aggregationChatCount(aggregationDto.getSpotNo(), aggregationDto.getIsIncrease());
				break;
		}
	}

	@Transactional
	public void aggregationChatCount(Long spotNo, String isIncrease) {
		if (isIncrease.equals("true")) {
			aggregationRepository.increaseChatCount(spotNo);
			return;
		}

		aggregationRepository.decreaseChatCount(spotNo);
	}*/


}
