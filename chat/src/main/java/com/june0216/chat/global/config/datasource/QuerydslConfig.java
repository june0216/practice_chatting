package com.june0216.chat.global.config.datasource;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
public class QuerydslConfig {

	@Bean
	public JPAQueryFactory queryFactory(EntityManager entityManager) {
		return new JPAQueryFactory(entityManager);
	}
}
