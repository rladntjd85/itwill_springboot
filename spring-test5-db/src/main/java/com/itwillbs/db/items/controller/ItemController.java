package com.itwillbs.db.items.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.db.items.constant.ItemCategory;
import com.itwillbs.db.items.dto.ItemDTO;
import com.itwillbs.db.items.service.ItemService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/items")
@Log4j2
public class ItemController {
	private final ItemService itemService;
	
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	// ----------------------------------------------
	@GetMapping("/new")		
	public String showRegistForm(Model model) {
		// ItemDTO 객체를 생성하여 Model 객체에 추가
		model.addAttribute("itemDTO", new ItemDTO());
		
		return "/items/item_regist_form";
	}
	
	// -----------------------------------------
	// POST 방식으로 요청되는 "/items" 요청 매핑
	// => 파라미터로 전달되는 값들을 ItemDTO 타입으로 바인딩
	// ItemDTO 객체에 바인딩되는 파라미터들에 대한 Validation check 수행을 위해 ItemDTO 타입 변수 선언부에 @Valid 어노테이션 지정
	// => 체크 결과를 뷰페이지에서 활용하기 위해 뷰페이지에서 사용할 DTO 객체 이름을 @ModelAttribute 어노테이션 속성으로 명시
	// => 전달된 파라미터들이 ItemDTO 객체에 바인딩되는 시점에 입력값 검증을 수행하고 이 결과를 BindingResult 타입 객체에 저장함
	// 업로드 파일들을 묶음으로 관리할 List<MultipartFiles> 타입 파라미터 선언
	@PostMapping("")
	public String registItem(@ModelAttribute("itemDTO") @Valid ItemDTO itemDTO, BindingResult bindingResult, 
			@RequestParam("itemImgFiles") List<MultipartFile> itemImgFiles, Model model) throws IOException {
//		log.info(">>>>>>>>>>>> itemDTO : " + itemDTO);
		
		// BindingResult 객체의 각각의 메서드 호출하여 입력값 검증 결과 얻어올 수 있음
//		log.info(">>>>>>>>>>>> bindingResult.getAllErrors() : " + bindingResult.getAllErrors()); // 전체 검증 결과에 대한 정보 확인
//		log.info(">>>>>>>>>>>> bindingResult.hasErrors() : " + bindingResult.hasErrors()); // 검증 오류 발생 여부(true/false) 리턴
		
//		log.info(">>>>>>>>>>>> 업로드 파일 갯수 : " + itemImgFiles.size());
//		log.info(">>>>>>>>>>>> 업로드 파일 첫번째 : " + itemImgFiles.get(0));
//		log.info(">>>>>>>>>>>> 업로드 파일 첫번째 존재 여부 : " + itemImgFiles.get(0).isEmpty());
		
		
		// 입력값 검증 결과가 true 일 때(= 입력값 오류 있음) 다시 입력폼으로 포워딩
		if(bindingResult.hasErrors()) {
			// 이 때, Model 객체를 활용하여 ItemDTO 객체나 BindingResult 객체를 저장하지 않아도 자동으로 뷰페이지로 전송됨
			return "/items/item_regist_form";
		}
		
		if(itemImgFiles.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "최소 한 개 이미지 등록 필수!");
			return "/items/item_regist_form";
		}
		// -----------------------------------------------------------------------
		// ItemService - registItem() 메서드 호출하여 상품 정보 등록 요청
		// => 파라미터 : ItemDTO 객체, 파일목록이 저장된 List<MultipartFile> 객체
		//    리턴타입 : 상품번호(Long 타입)
		Long itemId = itemService.registItem(itemDTO, itemImgFiles);
		log.info(">>>>>>>>>>>> itemId : " + itemId);
		
		// 등록 과정에서 리턴받은 상품아이디값을 /items 경로에 결합하여 상품 상세정보 조회 페이지 리다이렉트
		return "redirect:/items/" + itemId;
	}
	// ============================================================
	// 상품 상세정보 조회 요청 처리
	// => http://localhost:8085/items/1002 형태로 /items 뒤의 경로로 상품 아이디 전달됨
	@GetMapping("/{itemId}")
	public String itemDetail(@PathVariable("itemId") Long itemId, Model model) {
		// ItemService - getItem() 메서드 호출하여 상품 1개 상세정보 조회 요청
		// => 파라미터 : 상품번호(Long)   리턴타입 : ItemDTO(itemDTO)
		ItemDTO itemDTO = itemService.getItem(itemId);
		model.addAttribute("itemDTO", itemDTO);
		
		
		return "/items/item_detail";
	}
	
	// 상품 첨부파일 다운로드 요청 처리
	@GetMapping("/download/{itemImgId}")
	public ResponseEntity<Resource> getFile(@PathVariable("itemImgId") Long itemImgId) {
		
		return null;
	}
	
	// ============================================================
	
}

























