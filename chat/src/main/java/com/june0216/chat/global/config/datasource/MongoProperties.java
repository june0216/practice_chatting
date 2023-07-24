package com.june0216.chat.global.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "mongodb")
public class MongoProperties {
	String client;
	String name;
}
