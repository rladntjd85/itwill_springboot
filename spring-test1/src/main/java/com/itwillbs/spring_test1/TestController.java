package com.itwillbs.spring_test1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


// 컨트롤러 역할 클래스는 @Controller 어노테이션 지정
@Controller
public class TestController {
	
	// 클라이언트로부터 루트(/) 경로 요청 발생하면 현재 메서드의 주소와 매핑되고
	// 해당 매핑 어노테이션 아래쪽의 메서드가 자동으로 호출됨!
//	@ResponseBody
	@GetMapping("/")
	public String hello() {
//		return "<h1>Hello, World!!!!!!!!!!!!</h1>";
		// => 일반 컨트롤러 클래스에서 @ResponseBody 어노테이션이 없는 매핑 메서드의 경우
		//    return 문 뒤에 오는 문자열은 렌더링할 응답 페이지가 저장된 페이지 파일명을 지정한 것으로 간주
		// => 단, @ResponseBody 어노테이션이 지정된 메서드일 경우 return 문 뒤에 기술된 데이터가 그대로 body 형태로 전송(응답)됨
		// --------------------
		// 스프링부트 컨트롤러에서 기본적으로 return 문 뒤에 파일명 작성하면 해당 파일의 내용을 렌더링하여 응답으로 전송
		// => @ResponseBody 어노테이션이 있을 경우 파일명이 아닌 단순 텍스트로 응답됨
		// 스프링부트 애플리케이션에서 렌더링 할 페이지의 기본 경로 : src/main/resources/static
		return "index.html";
//		return "main.html";
	}
	
	
}
















