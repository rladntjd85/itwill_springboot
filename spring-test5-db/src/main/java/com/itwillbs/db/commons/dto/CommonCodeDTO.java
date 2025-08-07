package com.itwillbs.db.commons.dto;

import org.modelmapper.ModelMapper;

import com.itwillbs.db.commons.entity.CommonCode;
import com.itwillbs.db.items.dto.ItemImgDTO;
import com.itwillbs.db.items.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonCodeDTO {
	private Long id;
	private String codeGroup; // 상위공통코드(공통코드그룹)
	private String commonCode; // 공통코드
	private String commonCodeName; // 공통코드명(코드를 한국어로 표기하기 위함)
	private String description; // 공통코드 상세설명
	
	// ------------------------------
	// CommonCode <-> CommonCodeDTO 변환
	private static ModelMapper modelMapper = new ModelMapper();
	
	public CommonCode toEntity() {
		return modelMapper.map(this, CommonCode.class);
	}
	
	public static CommonCodeDTO fromEntity(CommonCode commonCode) {
		return modelMapper.map(commonCode, CommonCodeDTO.class);
	}
}





















