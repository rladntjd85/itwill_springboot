package com.itwillbs.db.items.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.db.commons.dto.LotDTO;
import com.itwillbs.db.commons.dto.NotificationDTO;
import com.itwillbs.db.items.dto.ItemDTO;
import com.itwillbs.db.items.dto.ItemImgDTO;
import com.itwillbs.db.items.service.ItemImgService;
import com.itwillbs.db.items.service.ItemService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/items")
@Log4j2
public class ItemController {
	private final ItemService itemService;
	private final ItemImgService itemImgService;
	
	// STOMP 메세지 전송을 처리할 SimpMessageTemplate 객체 주입
	// => 스프링 빈이라면 어디서든 SimpMessageTemplate 객체로 메세지 전송 가능
	private final SimpMessagingTemplate messagingTemplate;
	
	public ItemController(ItemService itemService, ItemImgService itemImgService, SimpMessagingTemplate messagingTemplate) {
		this.itemService = itemService;
		this.itemImgService = itemImgService;
		this.messagingTemplate = messagingTemplate;
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
		
		// ========================================================================================
		// SimpMessagingTemplate 객체를 사용하여 웹소켓 연결된 클라이언트의 특정 채널에 메세지 전송
		NotificationDTO notificationDTO = NotificationDTO.builder()
				.messageType("NOTIFICATION/newItem")
				.message("새 상품 등록됨!")
				.createAt(LocalDateTime.now())
				.build();
		// SimpMessagingTemplate 객체의 convertAndSend() 메서드 호출하여 특정 채널 구독자에게 메세지 전송
		// => 첫번째 파라미터 : 구독 채널 주소("/topic/xxx")
		//    두번째 파라미터 : 전송할 메세지 => 자동으로 JSON 형식으로 변환 처리됨
		messagingTemplate.convertAndSend("/topic/noti", notificationDTO);
		// ========================================================================================
		
		// 등록 과정에서 리턴받은 상품아이디값을 /items 경로에 결합하여 상품 상세정보 조회 페이지 리다이렉트
		return "redirect:/items/" + itemId;
	}
	
	@ResponseBody
	@PostMapping("/registAjax")
	public Map<String, Object> registItemAjax(@ModelAttribute("itemDTO") @Valid ItemDTO itemDTO, BindingResult bindingResult, 
			@RequestParam("itemImgFiles") List<MultipartFile> itemImgFiles, Model model) throws IOException {
		// 입력값 검증 결과가 true 일 때(= 입력값 오류 있음) 다시 입력폼으로 포워딩
//		if(bindingResult.hasErrors()) {
//			// 이 때, Model 객체를 활용하여 ItemDTO 객체나 BindingResult 객체를 저장하지 않아도 자동으로 뷰페이지로 전송됨
//			return "/items/item_regist_form";
//		}
//		
//		if(itemImgFiles.get(0).isEmpty()) {
//			model.addAttribute("errorMessage", "최소 한 개 이미지 등록 필수!");
//			return "/items/item_regist_form";
//		}
		// -----------------------------------------------------------------------
		Long itemId = itemService.registItem(itemDTO, itemImgFiles);
		log.info(">>>>>>>>>>>> itemId : " + itemId);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("itemId", itemId);
		
		// 등록 과정에서 리턴받은 상품아이디값을 /items 경로에 결합하여 상품 상세정보 조회 페이지 리다이렉트
//		return "redirect:/items/" + itemId;
		return responseMap;
	}
	
	// ============================================================
	// 상품 상세정보 조회 요청 처리
	// => http://localhost:8085/items/1002 형태로 /items 뒤의 경로로 상품 아이디 전달됨
	@GetMapping("/{itemId}")
	public String itemDetail(@PathVariable("itemId") Long itemId, Model model) {
		LotDTO lotDTO = new LotDTO();
		itemService.registLot(lotDTO);
		
		// ItemService - getItem() 메서드 호출하여 상품 1개 상세정보 조회 요청
		// => 파라미터 : 상품번호(Long)   리턴타입 : ItemDTO(itemDTO)
		ItemDTO itemDTO = itemService.getItem(itemId);
		model.addAttribute("itemDTO", itemDTO);
		
		
		return "/items/item_detail";
	}
	
	// 상품 첨부파일 다운로드 요청 처리
	@GetMapping("/download/{itemImgId}")
	public ResponseEntity<Resource> getFile(@PathVariable("itemImgId") Long itemImgId) {
		log.info("INFO >>>>>>>>>>>>>>> itemImgId : " + itemImgId); 
		log.warn("WARN >>>>>>>>>>>>>>> itemImgId : " + itemImgId); 
		log.error("ERROR >>>>>>>>>>>>>>> itemImgId : " + itemImgId); 
		log.fatal("FATAL >>>>>>>>>>>>>>> itemImgId : " + itemImgId); 
		
		// ItemImgService - getItemImg() 메서드 호출하여 상품이미지 1개 정보 조회 요청
		// => 파라미터 : 상품이미지번호(itemImgId)   리턴타입 : ItemImgDTO(itemImgDTO)
//		ItemImgDTO itemImgDTO = itemImgService.getItemImg(itemImgId);
//		System.out.println(itemImgDTO);
		
		// 권한 확인 등의 코드.... 생략
		
		// ItemImgService - getDownloadResponse() 메서드 호출하여 다운로드 파일 요청
		// => 파라미터 : ItemImgDTO 객체   리턴타입 : ResponseEntity<Resource>(responseEntity)
//		ResponseEntity<Resource> responseEntity = itemImgService.getDownloadResponse(itemImgDTO);
		// => 파라미터 : 상품이미지번호(itemImgId)   리턴타입 : ResponseEntity<Resource>(responseEntity)
//		ResponseEntity<Resource> responseEntity = itemImgService.getDownloadResponse(itemImgId);
		return itemImgService.getDownloadResponse(itemImgId);
	}
	
	// ============================================================
//	@ResponseBody
//	@GetMapping("/list")
//	public List<ItemDTO> getItemList(Model model) {
//		// ItemService - getItemlist() 메서드 호출하여 상품목록 조회 요청
//		// => 파라미터 : 없음   리턴타입 : List<ItemDTO>(itemList)
//		return itemService.getItemList();
//	}
	@GetMapping("/list")
	public String getItemList(Model model) {
		// ItemService - getItemlist() 메서드 호출하여 상품목록 조회 요청
		// => 파라미터 : 없음   리턴타입 : List<ItemDTO>(itemList)
		List<ItemDTO> itemDTOList = itemService.getItemList();
		model.addAttribute("itemDTOList", itemDTOList);
		
		return "/items/item_list";
	}
	
	
	
}

























