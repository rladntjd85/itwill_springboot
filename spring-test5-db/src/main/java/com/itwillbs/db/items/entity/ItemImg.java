package com.itwillbs.db.items.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class ItemImg {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name = "item_img_id", updatable = false)
	private Long id; // 이미지번호
	private String imgName; // 이미지 파일명
	private String originalImgName; // 원본 이미지 파일명
	private String imgLocation; // 이미지 파일 위치
	private String repImgYn; // 대표이미지 설정 여부(Y/N)
	
	// ----------------------------------------------------------------------------------
	// 상품(1) : 상품이미지(N) 연관관계 매핑
	// 현재 ItemImg 엔티티 기준 N : 1 연관관계이므로 @ManyToOne 어노테이션을 사용하여 표현
	@ManyToOne
	@JoinColumn(name = "item_id") // 외래키(FK) 이름(Item 테이블의 참조할 컬럼명 지정)
	private Item item;
}





















