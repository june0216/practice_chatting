package com.june0216.chat.domain.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.june0216.chat.domain.chatting.domain.SpotAggregation;


public interface AggregationRepository extends JpaRepository<SpotAggregation, Long> {

		/*@Transactional(propagation = Propagation.REQUIRES_NEW)
		@Modifying(clearAutomatically = true)
		@Query("update SpotAggregation a set a.chatCount = a.chatCount - 1 where a.spotNo = :spotNo")
		void decreaseChatCount(@Param("spotNo") Long spotNo);*/

	/*	@Transactional(propagation = Propagation.REQUIRES_NEW)
		@Modifying(clearAutomatically = true)
		@Query("update SpotAggregation a set a.chatCount = a.chatCount + 1 where a.spotNo = :spotNo")
		void increaseChatCount(@Param("spotNo") Long spotNo);


		@Transactional(propagation = Propagation.REQUIRES_NEW)
		@Modifying(clearAutomatically = true)
		@Query("delete SpotAggregation a where a.spotNo = :spotNo")
		void removeAggregation(@Param("spotNo") Long spotNo);*/

}
