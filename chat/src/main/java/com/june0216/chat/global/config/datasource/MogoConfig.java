package com.june0216.chat.global.config.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.june0216.chat.global.config.properties.MongoProperties;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMongoRepositories(basePackages = "com.june0216.chat.domain.chatting.repository")
public class MogoConfig {
	private final MongoProperties mongoProperties;

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(mongoProperties.getClient());
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), mongoProperties.getName());
	}
}

