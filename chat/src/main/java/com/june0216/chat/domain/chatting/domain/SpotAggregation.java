package com.june0216.chat.domain.chatting.domain;



import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADOPT_AGGREGATION")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotAggregation {

	@Id
	@Column(name = "spot_no")
	private Long spotNo;

	@Column(name = "chat_count")
	private Integer chatCount;


	@Builder
	public SpotAggregation(Long spotNo, Integer chatCount) {
		this.spotNo = spotNo;
		this.chatCount = chatCount;
	}
}

