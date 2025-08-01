package com.itwillbs.thymeleaf.item;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class ItemDTO {
	private Long id; // 상품코드
	private String itemNm; // 상품명
	private String itemDetail; // 상품상세설명
	private int price; // 상품가격
	private int stockQty; // 상품재고수량
	private LocalDateTime regTime; // 상품등록일시
	
	// -----------------------------------------------------
	// 파라미터 생성자 정의
	@Builder // 빌디 패턴 형식으로 객체 생성하도록 하는 어노테이션
	public ItemDTO(Long id, String itemNm, String itemDetail, int price, int stockQty, LocalDateTime regTime) {
		this.id = id;
		this.itemNm = itemNm;
		this.itemDetail = itemDetail;
		this.price = price;
		this.stockQty = stockQty;
		this.regTime = regTime;
	}
	
	
	
}























