package com.june0216.chat.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "kafka.adopt")
public class KafkaAdoptProperties {
	private String topic;
	private String groupId;
	private String broker;
}
