package com.itwillbs.db.commons.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {
	private Long messageId;
	private String messageType;
	private String roomId;
	private String sender;
	private String message;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // 단순 문자열 형태의 "yyyy-MM-dd HH:mm:ss" 패턴 변환
	private LocalDateTime sentAt;
	
	@Builder
	public MessageDTO(Long messageId, String messageType, String roomId, String sender, String message,
			LocalDateTime sentAt) {
		this.messageId = messageId;
		this.messageType = messageType;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
		this.sentAt = sentAt;
	}
	
}
