package com.june0216.chat.global.config.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.june0216.chat.global.config.interceptor.StompHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // WebSocket을 활성화하고 메시지 브로커 사용가능
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
	private final StompHandler stompHandler;

	// STOMP 엔드포인트를 등록하는 메서드
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) { //Stomp를 지원하지 않는 브라우저나 환경에서 사용할경우 SockJS를 사용한 emulataion을 하기 위해 SockJS 사용이 가능하도록 설정
		registry.addEndpoint("/chat") // STOMP 엔드포인트 설정
			.setAllowedOriginPatterns("*"); // 모든 Origin 허용 -> 배포시에는 보안을 위해 Origin을 정확히 지정
			//.withSockJS(); // SockJS 사용가능 설정
	}

	// 메시지 브로커를 구성하는 메서드
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/subscribe"); // /subscribe/{chatNo}로 주제 구독 가능
		registry.setApplicationDestinationPrefixes("/publish"); // /publish/message로 메시지 전송 컨트롤러 라우팅 가능
	}

	// 클라이언트 인바운드 채널을 구성하는 메서드
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// stompHandler를 인터셉터로 등록하여 STOMP 메시지 핸들링을 수행
		registration.interceptors(stompHandler);
	}

	// STOMP에서 64KB 이상의 데이터 전송을 못하는 문제 해결
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setMessageSizeLimit(160 * 64 * 1024);
		registry.setSendTimeLimit(100 * 10000);
		registry.setSendBufferSizeLimit(3 * 512 * 1024);
	}
}
