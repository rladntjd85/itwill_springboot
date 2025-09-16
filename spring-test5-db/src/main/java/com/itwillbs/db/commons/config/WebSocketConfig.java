package com.itwillbs.db.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// 웹소켓 설정
@Configuration
@EnableWebSocketMessageBroker // STOMP 메세지 브로커 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	// 클라이언트가 연결할 엔드포인트 등록
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws") // 클라이언트의 웹소켓 연결할 요청 주소를 "/ws" 로 지정하여 엔드포인트 연결
//				.setAllowedOriginPatterns("*") // CORS 허용
				.setAllowedOriginPatterns("http://localhost:8085") // CORS 허용X (현재 서버 내에서의 요청만 허용)
				.withSockJS(); // SockJS Fallback 활성화
	}

	// 메세지 전송을 처리하는 메세지 브로커 설정
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 메세지 브로커가 "/topic/xxx" 형태로 발생된 메세지를 구독자(xxx)에게 전달
		registry.enableSimpleBroker("/topic");
		// 클라이언트가 "/app/xxx" 형태로 보낸 메세지를 @MessagingMapping 어노테이션으로 라우팅
		registry.setApplicationDestinationPrefixes("/app");
	}
	
}


















