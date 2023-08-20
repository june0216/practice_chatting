package com.june0216.chat.domain.chatting.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.june0216.chat.domain.chatting.dto.aggregation.AggregationDto;
import com.june0216.chat.global.util.ConstantUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregationReceiver {

	private final AggregationService aggregationService;

	@KafkaListener(topics = ConstantUtil.KAFKA_AGGREGATION, containerFactory = "kafkaAggregationContainerFactory")
	public void aggregation(AggregationDto aggregationDto) {
		log.info("요청 도착 = {}", aggregationDto);
		//aggregationService.aggregation(aggregationDto);
	}
}
