package com.itwillbs.db.items.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.itwillbs.db.items.constant.ItemCategory;
import com.itwillbs.db.items.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
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
// @CreatedDate 등의 어노테이션을 활용하여 날짜 정보 등을 자동으로 등록하기 위해 JPA 감사(Auditing) 기능 사용 시
// 감사 기능이 동작해야하는 클래스(엔티티 또는 공통감사엔티티)에 @EntityListeners 어노테이션 추가하여 감사 대상 클래스로 지정하고
// 스프링 메인클래스(XXXAplication) 또는 스프링 설정용 클래스에 @EnableJpaAuditing 어노테이션 추가 필수!
@EntityListeners(AuditingEntityListener.class)
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
	// 엔티티를 통해 DB 에 enum 타입 데이터 연결 시 String 타입으로 처리하도록 하기
	// => enum 타입을 DB 의 기본타입인 enum 타입으로 사용 시 번호 형태로 관리되는데
	//    enum 상수 순서가 변경되면 바뀌게 되므로 추천하지 않음!
	@Enumerated(EnumType.STRING) // enum 타입 데이터를 String 타입으로 처리(EnumType.ORDINAL : enum 타입 데이터를 숫자 형태로 처리)
	@Column(nullable = false)
	private ItemCategory category;
	
	@Enumerated(EnumType.STRING) // enum 타입 데이터를 String 타입으로 처리(EnumType.ORDINAL : enum 타입 데이터를 숫자 형태로 처리)
	@Column(nullable = false)
	private ItemSellStatus sellStatus;
	// ----------------------------------------------------------------------------------
	// JPA 에서 특정 날짜 및 시각(또는 다른 형식도 가능) 필드 값을 자동으로 설정하려면
	// JPA 감사(Auditing) 기능을 사용하기 위한 어노테이션 조합을 통해 자동 등록 필드로 지정
	// => 자동 등록될 필드가 위치한 클래스(엔티티)와 스프링 메인 클래스에 어노테이션 설정 추가 필요
	@CreatedDate // 엔티티 최초 생성 시점에 날짜 및 시각이 자동으로 등록되는 필드로 설정
	@Column(updatable = false) // 해당 필드는 수정 불가능한 필드로 설정
	private LocalDateTime regTime; // 상품등록일시
	
	@LastModifiedDate // 엔티티 변경 시점에 날짜 및 시각이 자동으로 갱신되는 필드
	private LocalDateTime updateTime; // 상품최종수정일시
	// ----------------------------------------------------------------------------------
	// 상품(1) : 상품이미지(N) 연관관계 매핑
	// => 1에 해당하는 엔티티 클래스에서 @OneToMany 어노테이션 사용하여 연관관계 지정
	// => mappedBy 속성에 지정하는 이름은 상대 엔티티의 자신의 필드명을 지정하며, 이는 자신이 연관관계의 주인임을 의미
	// => fetch = FetchType.LAZY 속성 설정 시 Item 엔티티를 조회할 때 무조건 ItemImg 엔티티를 조회하는 것이 아니라
	//    [지연로딩] 에 의해 해당 엔티티(ItemImg)가 실제로 사용되는 시점에 조회가 일어남. 따라서, 조회 성능이 향상됨   
	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<ItemImg> itemImgs = new ArrayList<ItemImg>();
	// ===============================================================
	// id 값을 제외한 필수값에 대한 생성자 정의(날짜도 제외)
	@Builder
	public Item(String itemNm, String itemDetail, int price, int stockQty, ItemCategory category,
			ItemSellStatus sellStatus) {
		this.itemNm = itemNm;
		this.itemDetail = itemDetail;
		this.price = price;
		this.stockQty = stockQty;
		this.category = category;
		this.sellStatus = sellStatus;
	}
	
}





















