package com.itwillbs.thymeleaf;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.thymeleaf.item.ItemDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;


@Controller
@RequestMapping("/thymeleaf")
@Log4j2
public class ThymeleafController {
	// 단일 데이터를 Model 객체에 저장하여 타임리프 템플릿 페이지에서 출력하기
	@GetMapping("/test1")
	public String test1(Model model) {
		// Model 객체에 "data" 라는 속성명으로 단일 텍스트 저장
		model.addAttribute("data", "타임리프 예제");
		
		// Model 객체에 "data2" 라는 속성명으로 아무 데이터나 저장
		model.addAttribute("data2", 100);
		
		// return 문 뒤에 포워딩 할 뷰페이지 명시할 때
		// 포워딩 기준이 되는 경로는 src/main/resources/templates 디렉토리이다.
		return "thymeleaf_test1"; // 확장자명 (.html) 생략 가능
	}
	
	// ----------------------------------------------------------
	// 단일 객체를 뷰페이지에서 출력하기
	@GetMapping("/test2")
	public String test2(Model model) {
		// ItemDTO 객체 생성
		// 1) 기본 생성자 활용
//		ItemDTO itemDTO = new ItemDTO();
//		itemDTO.setId(1L);
//		itemDTO.setItemNm("상품1");
//		itemDTO.setItemDetail("상품1 상세설명");
//		itemDTO.setPrice(10000);
//		itemDTO.setStockQty(10);
//		itemDTO.setRegTime(LocalDateTime.now());
		
		// 2) 파라미터 생성자 활용
//		ItemDTO itemDTO = new ItemDTO(1L, "상품1", "상품1 상세설명", 10000, 10, LocalDateTime.now());
		
		// 3) 파라미터 생성자에 대한 빌더 패턴(Builder Pattern) 활용
		// => Lombok 에서 제공하는 @Builder 어노테이션을 파라미터 생성자에 선언하면
		//    클래스명.builder() 메서드 호출 뒤에 필드값 초기화하는 메서드를 연쇄적으로 호출하여 값을 설정하고
		//    마지막에 build() 메서드를 호출하면 해당 객체가 생성되어 리턴됨
		ItemDTO itemDTO = ItemDTO.builder()
				.id(2L)
				.itemNm("테스트 상품1")
				.itemDetail("테스트 상품1 상세설명")
				.price(1000)
				.stockQty(100)
				.regTime(LocalDateTime.now())
				.build();
		
		// Model 객체에 데이터가 저장된 객체 전달
		model.addAttribute("itemDTO", itemDTO);
		
		return "thymeleaf_test2";
	}
	
	// ----------------------------------------------------------
	// 다중 객체(리스트)를 Model 객체에 저장하여 타임리프 템플릿 페이지에서 출력하기
	@GetMapping("/test3")
	public String test3(Model model) {
		List<ItemDTO> itemList = new ArrayList<>();
		
		for(int i = 1; i <= 10; i++) {
			ItemDTO itemDTO = ItemDTO.builder()
					.id((long)i)
					.itemNm("테스트 상품" + i)
					.itemDetail("테스트 상품" + i + " 상세설명")
					.price(10000 * i)
					.stockQty(new SecureRandom().nextInt(10 * i))
					.regTime(LocalDateTime.now())
					.build();
			
			itemList.add(itemDTO);
		}
		
		model.addAttribute("itemList", itemList);
		
		return "thymeleaf_test3";
	}
		
	// ----------------------------------------------------------
	// 조건문 연습
	@GetMapping("/test4")
	public String test4(Model model) {
		List<ItemDTO> itemList = new ArrayList<>();
		
		for(int i = 1; i <= 10; i++) {
			ItemDTO itemDTO = ItemDTO.builder()
					.id((long)i)
					.itemNm("테스트 상품" + i)
					.itemDetail("테스트 상품" + i + " 상세설명")
					.price(10000 * i)
					.stockQty(new SecureRandom().nextInt(10 * i))
					.regTime(LocalDateTime.now())
					.build();
			
			itemList.add(itemDTO);
		}
		
		model.addAttribute("itemList", itemList);
		
		return "thymeleaf_test4";
	}
	
	// ----------------------------------------------------------
	// 하이퍼링크 연습
	@GetMapping("/test5")
	public String test5(Model model) {
		model.addAttribute("data", "테스트데이터");
		
		return "thymeleaf_test5";
	}
	
	// System.out.println() 등의 출력문 대신 로그 메세지로 출력하는 것이 성능 향상에 도움이 된다!
	// 롬복의 @Log4j2 어노테이션을 지정하면 log.xxx() 메서드로 로그메세지 출력 가능
	@GetMapping("/test5-2")
	public String test5_2(@RequestParam Map<String, String> params, Model model, HttpSession session) {
		log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ params : " + params.toString());
		log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ param1 : " + params.get("param1"));
		log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ param2 : " + params.get("param2"));
		log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ param3 : " + params.get("param3"));
		
		model.addAttribute("params", params);
		// --------------------------------------------------------
		// 세션 객체에 "id" 라는 속성으로 "admin" 문자열 저장
		session.setAttribute("id", "admin");
		
		return "thymeleaf_test5-2";
	}
	// ============================================================
	// 타임리프 레이아웃
	@GetMapping("/test6")
	public String test6() {
		return "thymeleaf_test6_layout";
	}
	
	@GetMapping("/test6-2")
	public String test6_2() {
		return "thymeleaf_test6-2";
	}
	
	@GetMapping("/test6-3")
	public String test6_3() {
		return "thymeleaf_test6-3";
	}
	
	@GetMapping("/test7")
	public String test7() {
		return "thymeleaf_test7";
	}
	
	
}




















