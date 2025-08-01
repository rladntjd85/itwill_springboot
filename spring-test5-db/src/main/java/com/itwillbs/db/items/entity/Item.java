package com.itwillbs.db.items.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 상품 엔티티 클래스
@Entity
@Table(name = "items")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(updatable = false)
	private Long id; // 상품번호
	
	@Column(length = 100, nullable = false)
	private String itemNm; // 상품명
	
	@Column(nullable = false)
	private String itemDetail; // 상품상세설명
	
	private int price; // 상품가격
	private int stockQty; // 상품재고수량
	
	// ----------------------------------------------------------------------------------
	
	
	// ----------------------------------------------------------------------------------
	// JPA 에서 특정 날짜 및 시각(또는 다른 형식도 가능) 필드 값을 자동으로 설정하려면
	// JPA 감사(Auditing) 기능을 사용하기 위한 어노테이션 조합을 통해 자동 등록 필드로 지정
	// => 자동 등록될 필드가 위치한 클래스(엔티티)와 스프링 메인 클래스에 어노테이션 설정 추가 필요
	@CreatedDate // 엔티티 최초 생성 시점에 날짜 및 시각이 자동으로 등록되는 필드로 설정
	@Column(updatable = false) // 해당 필드는 수정 불가능한 필드로 설정
	private LocalDateTime regTime; // 상품등록일시
	
	@LastModifiedDate // 엔티티 변경 시점에 날짜 및 시각이 자동으로 갱신되는 필드
	private LocalDateTime updateTime; // 상품최종수정일시
	
	
}





















