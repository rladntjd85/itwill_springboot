package com.itwillbs.db.commons.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NotificationDTO { // 웹소켓 알림 메세지 관리할 클래스
	private Long messageId;
	private String messageType;
	private String message;
	// LocalXXX 타입을 JSON 형식으로 직렬화/역직렬화 시 사용할 포맷 지정
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // 단순 문자열 형태의 "yyyy-MM-dd HH:mm:ss" 패턴 변환
	private LocalDateTime createAt;
	
	@Builder // 파라미터 생성자에 대한 빌더 패턴 제공
	public NotificationDTO(Long messageId, String messageType, String message, LocalDateTime createAt) {
		super();
		this.messageId = messageId;
		this.messageType = messageType;
		this.message = message;
		this.createAt = createAt;
	}
	
	
}
















