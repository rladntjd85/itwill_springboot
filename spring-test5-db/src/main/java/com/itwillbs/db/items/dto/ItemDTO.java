package com.itwillbs.db.items.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.itwillbs.db.items.constant.ItemCategory;
import com.itwillbs.db.items.constant.ItemSellStatus;
import com.itwillbs.db.items.entity.Item;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemDTO {
	// Java Bean Validation 기능을 활용하여 입력값 검증 수행 => Validation 의존성(spring-boot-starter-validation) 추가 필요
	// => 다양한 어노테이션을 조합하여 검증 조건 설정. 컨트롤러에서 바인딩 시점에 체크 가능
	// => 기본 문법 : @XXX(message = "오류 발생 시 표시할 메세지") 
	//    (필요에 따라 value 속성을 통한 값 지정도 가능)
	// @NotEmpty : 문자열이 null 이거나 길이가 0인 문자열(널스트링)을 허용하지 않음
	// @NotBlank : 문자열이 null 이거나 길이가 0인 문자열(널스트링) 또는 공백만 포함한 문자열을 허용하지 않음
	// @NotNull : 객체값 null 을 허용하지 않음
	// @Positive : 0 제외 양수만 허용(x > 0)
	// @PositiveOrZero : 0 이상의 양수만 허용(x >= 0)
	// @Negative : 0 제외 음수만 허용(x < 0)
	// @NegativeOrZero : 0 이하의 음수만 허용(x <= 0)
	// @Min(value) : 최소값 제한(x >= value)
	// @Max(value) : 최대값 제한(x <= value)
	// @Pattern(regexp = "정규표현식패턴문자열") : 특정 정규표현식에 매칭되는 문자열 탐색 후 일치하지 않을 경우 허용하지 않음
	// ---------------------------------------------------------------------------------------------------------------------
	private Long id; // 상품번호
	
	@NotBlank(message = "상품명은 필수 입력값입니다!") // 공백만 있거나, 길이가 0인 문자열, null 값을 허용하지 않음
	@Pattern(regexp = "^[A-Za-z가-힣0-9]{1,100}$", message = "상품명은 영문자, 숫자, 한글 1~100글자만 사용 가능합니다!")
	private String itemNm; // 상품명
	
	@NotEmpty(message = "상품명은 필수 입력값입니다!") // 길이가 0인 문자열, null 값을 허용하지 않음
	private String itemDetail; // 상품상세설명
	
	// int 타입으로 필드 선언 시 값이 전달되지 않으면 기본값 0 이 저장되는데, 사용자가 입력한 0과 실수로 입력하지 않은 상황이 구별 불가능
	// => 따라서, int 대신 Integer 타입 사용 시 사용자가 실수로 값을 입력하지 않으면 참조 타입 기본값인 null 이 전달됨
	// => 숫자 데이터에 대한 값 체크는 @NotEmpty 가 아닌 @NotNull 사용하여 판별
	@NotNull(message = "가격은 필수 입력값입니다!")
	@PositiveOrZero(message = "가격은 최소 0원 이상 입력 가능합니다!")
	private Integer price; // 상품가격
	
	@NotNull(message = "수량은 필수 입력값입니다!")
	@Positive(message = "수량은 최소 1개 이상 입력 가능합니다!")
	@Max(value = 99999, message = "수량은 99999개를 초과할 수 없습니다!")
	private Integer stockQty; // 상품재고수량
	
	@NotNull(message = "상품카테고리 선택은 필수입니다!") // enum 타입값 검증 시 널스트링("") 전달되면 null 값으로 취급
	private ItemCategory category; // 상품카테고리(enum)
	
	@NotNull(message = "상품판매상태 선택은 필수입니다!") // enum 타입값 검증 시 널스트링("") 전달되면 null 값으로 취급
	private ItemSellStatus sellStatus; // 상품판매상태(enum)
	
	private LocalDateTime regTime; // 상품등록일시
	private LocalDateTime updateTime; // 상품최종수정일시
	
	// 상품 이미지 목록 정보를 저장할 List 타입 선언
	private List<ItemImgDTO> itemImgDTOList;
	// ======================================================
	// 파라미터 생성자 정의
	@Builder // 생성자 메서드를 빌더 패턴 형식으로 제공
	public ItemDTO(Long id, String itemNm, String itemDetail, Integer price, Integer stockQty, 
			ItemCategory category, ItemSellStatus sellStatus, 
			LocalDateTime regTime, LocalDateTime updateTime) {
		this.id = id;
		this.itemNm = itemNm;
		this.itemDetail = itemDetail;
		this.price = price;
		this.stockQty = stockQty;
		this.category = category;
		this.sellStatus = sellStatus;
		this.regTime = regTime;
		this.updateTime = updateTime;
	}
	// =======================================================
	// Item 엔티티 <-> ItemDTO 간의 변환
	// 1) Entity -> DTO 로 변환하는 fromEntity() 메서드 정의
	//    => 파라미터 : Entity 객체   리턴타입 : DTO 타입
	//    => 단, ItemDTO 객체가 없는 상태에서 Item 엔티티로부터 생성해야하므로 ItemDTO 내에서 static 메서드로 정의
	//       (또는, 메서드를 Item 엔티티 클래스에서 정의하면 됨)
//	public static ItemDTO fromEntity(Item item) {
//		return ItemDTO.builder()
//				.id(item.getId())
//				.itemNm(item.getItemNm())
//				.itemDetail(item.getItemDetail())
//				.price(item.getPrice())
//				.stockQty(item.getStockQty())
//				.category(item.getCategory())
//				.sellStatus(item.getSellStatus())
//				.regTime(item.getRegTime())
//				.updateTime(item.getUpdateTime())
//				.build();
//	}
	
	// 2) DTO -> Entity 로 변환하는 toEntity() 메서드 정의
	//    => 파라미터 : 없음(현재 DTO 객체(인스턴스) 사용)   리턴타입 : Entity 타입
//	public Item toEntity() {
//		return Item.builder()
//				.itemNm(itemNm)
//				.itemDetail(itemDetail)
//				.price(price)
//				.stockQty(stockQty)
//				.category(category)
//				.sellStatus(sellStatus)
//				.build();
//	}
	// ------------------------------
	// DTO <-> Entity 변환 메서드를 직접 구현하지 않고 ModelMapper 라이브러리를 활용하여 간편하게 구현
	// => 단, 기본적으로 두 클래스간의 필드명이 동일한 필드끼리만 자동으로 전달 처리됨
	private static ModelMapper modelMapper = new ModelMapper();
	
	// ModelMapper 객체의 map() 메서드를 활용하여 객체 변환 수행
	// 1) ItemDTO -> Item 타입으로 변환하는 toEntity() 메서드 정의
	public Item toEntity() {
		return modelMapper.map(this, Item.class);
	}
	
	// 2) Entity -> DTO 로 변환하는 fromEntity() 메서드 정의
	public static ItemDTO fromEntity(Item item) {
		return modelMapper.map(item, ItemDTO.class);
	}
	
}


























