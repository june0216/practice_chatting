package com.june0216.chat.global.config.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.RequiredArgsConstructor;

@Configuration//이 클래스가 스프링 IoC 컨테이너에 의해 빈 설정 정보를 제공하는 것을 나타냅니다. 즉, 이 클래스는 스프링 프레임워크에 의해 관리되는 빈 객체를 생성하고, 설정
@RequiredArgsConstructor
@EnableMongoRepositories(basePackages = "com.june0216.chat.global") //MongoDB 저장소를 활성화하고, 특정 패키지에 있는 리포지토리 인터페이스를 스캔
public class MogoConfig {
	private final MongoProperties mongoProperties;

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(mongoProperties.getClient());
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), mongoProperties.getName()); //mongoClient()를 사용하여 만든 MongoClient 인스턴스와 MongoProperties의 getName 메서드를 사용하여 데이터베이스 이름을 가져와 MongoTemplate 객체를 생성
	}
}
