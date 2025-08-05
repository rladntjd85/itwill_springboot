package com.itwillbs.db.items.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemImgDTO {
	private Long id; // 이미지번호
	private String imgName; // 이미지 파일명
	private String originalImgName; // 원본 이미지 파일명
	private String imgLocation; // 이미지 파일 위치
	private String repImgYn; // 대표이미지 설정 여부(Y/N)
	
	
}


























