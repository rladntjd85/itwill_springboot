package com.itwillbs.db.items.dto;

import org.modelmapper.ModelMapper;

import com.itwillbs.db.items.entity.Item;
import com.itwillbs.db.items.entity.ItemImg;

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
	
	// ------------------------------
	// DTO <-> Entity 변환 메서드를 직접 구현하지 않고 ModelMapper 라이브러리를 활용하여 간편하게 구현
	// => 단, 기본적으로 두 클래스간의 필드명이 동일한 필드끼리만 자동으로 전달 처리됨
	private static ModelMapper modelMapper = new ModelMapper();
	
	// ModelMapper 객체의 map() 메서드를 활용하여 객체 변환 수행
	// 1) ItemImgDTO -> ItemImg 타입으로 변환하는 toEntity() 메서드 정의
	public ItemImg toEntity() {
		return modelMapper.map(this, ItemImg.class);
	}
	
	// 2) Entity -> DTO 로 변환하는 fromEntity() 메서드 정의
	public static ItemImgDTO fromEntity(ItemImg itemImg) {
		return modelMapper.map(itemImg, ItemImgDTO.class);
	}
}


























