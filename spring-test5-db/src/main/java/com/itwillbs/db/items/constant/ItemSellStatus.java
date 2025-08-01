package com.itwillbs.db.items.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 상품의 판매상태를 관리할 enum
@Getter
@RequiredArgsConstructor
public enum ItemSellStatus {
	AVAILABLE("판매중"),
	OUT_OF_STOCK("품절"),
	DISCONTINUED("단종");
	
	private final String label;
	
	public String getCode() {
		// 현재 enum 객체의 name() 메서드 호출 시 자동으로 enum 상수값 리턴됨
		return this.name(); // enum 상수값을 외부로 리턴
	}
}






















