package com.itwillbs.db.items.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.db.items.dto.ItemDTO;
import com.itwillbs.db.items.entity.Item;
import com.itwillbs.db.items.repository.ItemRepository;

import jakarta.validation.Valid;

@Service
public class ItemService {
	private final ItemRepository itemRepository;

	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	// -------------------------------------------------
	// 상품 정보 등록 요청
	// => 상품 정보와 상품 이미지(첨부파일) 정보 등록 요청 위해 트랜잭션 처리
	@Transactional
	public Long registItem(ItemDTO itemDTO, List<MultipartFile> itemImgFiles) {
		// ItemDTO -> Item 엔티티 타입으로 변환
		Item item = itemDTO.toEntity();
		
		// ItemRepository - save() 메서드 호출하여 INSERT 작업 요청
		itemRepository.save(item);
		// => JPA 를 통해 INSERT 되는 과정에서 PK 값(Long id)이 자동 생성된 후 엔티티에 자동 저장됨
		
		
		
		return item.getId(); // 새로 등록된 엔티티의 id 값 리턴
	}
	
	
}














