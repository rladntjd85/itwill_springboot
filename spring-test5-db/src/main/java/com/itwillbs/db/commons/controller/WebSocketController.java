package com.itwillbs.db.commons.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.itwillbs.db.commons.dto.MessageDTO;

// 웹소켓 메세지 처리 컨트롤러 정의
@Controller
public class WebSocketController {
	// STOMP 메세지 전송을 처리할 SimpMessageTemplate 객체 주입
	// => 스프링 빈이라면 어디서든 SimpMessageTemplate 객체로 메세지 전송 가능
	private final SimpMessagingTemplate messagingTemplate;

	public WebSocketController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}
	// ------------------------------------------------------------------
	/*
	 * STOMP 클라이언트가 "/app/chat.sendMessage" 보낸 메세지 처리
	 * => Principal 객체(또는 Authentication 객체)를 사용하여 송신자(메세지 보낸 사용자) 정보 가져오기
	 * => 메세지 DB 에 저장하는 작업 등은 생략
	 * => 클라이언트로부터 전송된 메세지는 @messageMapping 어노테이션을 사용하여 매핑 처리
	 */
	@MessageMapping("/chat.sendMessage")
	public void sendMessage(MessageDTO message, Principal principal) {
		// 로그인 한 사용자명(사용자 아이디)을 송신자로 설정
		message.setSender(principal.getName());
		// 메세지 송신일시 생성
		message.setSentAt(LocalDateTime.now());
		
		// 채팅 메세지 DB 에 저장
//		chatService.saveMessage(message);
		
		// 특정 채팅방 구독자에게만 메세지 전송 => 전송 대상 채널 구분은 MessageDTO 객체의 roomId 값 활용
		// => 첫번째 파라미터 : 구독 채널 주소("/topic/xxx")
		//    두번째 파라미터 : 전송할 메세지 => 자동으로 JSON 형식으로 변환 처리됨
		messagingTemplate.convertAndSend("/topic/" + message.getRoomId(), message);
	}
	
	
	
}


















