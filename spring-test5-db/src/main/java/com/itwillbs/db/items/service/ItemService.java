package com.itwillbs.db.items.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.db.items.dto.ItemDTO;
import com.itwillbs.db.items.dto.ItemImgDTO;
import com.itwillbs.db.items.entity.Item;
import com.itwillbs.db.items.entity.ItemImg;
import com.itwillbs.db.items.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.FetchType;
import jakarta.validation.Valid;

@Service
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImgService itemImgService;

	public ItemService(ItemRepository itemRepository, ItemImgService itemImgService) {
		this.itemRepository = itemRepository;
		this.itemImgService = itemImgService;
	}
	// -------------------------------------------------
	// 상품 정보 등록 요청
	// => 상품 정보와 상품 이미지(첨부파일) 정보 등록 요청 위해 트랜잭션 처리
	@Transactional
	public Long registItem(ItemDTO itemDTO, List<MultipartFile> itemImgFiles) throws IOException {
		// ItemDTO -> Item 엔티티 타입으로 변환
		Item item = itemDTO.toEntity();
		
		// ItemRepository - save() 메서드 호출하여 INSERT 작업 요청
		itemRepository.save(item);
		// => JPA 를 통해 INSERT 되는 과정에서 PK 값(Long id)이 자동 생성된 후 엔티티에 자동 저장됨
		
		// ItemImgService - registItemImg() 메서드 호출하여 상품 이미지(파일) 등록 요청
		// => 파라미터 : Item 엔티티, 상품이미지(첨부파일목록) List 객체
		itemImgService.registItemImg(item, itemImgFiles);
		
		return item.getId(); // 새로 등록된 엔티티의 id 값 리턴
	}
	// ============================================================
	// 상품 1개 상세정보 조회 요청
	@Transactional(readOnly = true) // 단순 SELECT 를 위한 읽기전용 트랜잭션으로 설정하여 조회 성능 향상
	public ItemDTO getItem(Long itemId) {
		// 상품 1개 정보(= 1개 엔티티) 조회
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new EntityNotFoundException("해당 상품이 존재하지 않습니다!"));
		
		// Item -> ItemDTO 변환
		ItemDTO itemDTO = ItemDTO.fromEntity(item);
		System.out.println("itemDTO : " + itemDTO);
		
		// Item 엔티티 조회 시 List<ItemImg> 타입 필드는 FetchType.LAZY 설정에 의해 동시 조회가 일어나지 않는다!
		// 해당 데이터가 실제로 사용되는 시점에 조회가 일어남
		List<ItemImg> itemImgList = item.getItemImgs(); 
//		System.out.println("itemImgList : " + itemImgList); // 객체 실제 접근 시점인 이 코드가 실행될 때 조회 일어남
//		System.out.println("첨부파일 갯수 : " + itemImgList.size());
		// List<ItemImg> -> List<ItemImgDTO> 타입으로 변환
		// 1) 반복문을 사용하여 fromEntity() 메서드를 반복 호출하여 변환
		// 2) 스트림 활용하여 List<X> -> List<Y> 형태로 변환하는 코드
		itemDTO.setItemImgDTOList(itemImgList.stream() // List 객체에 대한 자바 스트림 생성(filter, map, collect 등으로 처리 가능)
//				.map(itemImg -> ItemImgDTO.fromEntity(itemImg)) // 스트림 내의 각 요소에 존재하는 함수를 활용하여 다른 타입 객체로 변환
				.map(ItemImgDTO::fromEntity) // 화살표함수 호출 형태를 축약형으로 표현한 문법(= 메서드 참조(Method Reference))
				.collect(Collectors.toList())); // ItemImgDTO 객체를 담는 List 객체 형태로 반환
		
		return itemDTO;
	}
	
	
}














