package com.june0216.chat.domain.chatting.dto.aggregation;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AggregationDto implements Serializable {

	private Long spotNo;
	private String isIncrease;
	private AggregationTarget target;
}
