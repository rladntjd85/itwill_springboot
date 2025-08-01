package com.itwillbs.spring_test1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


//@RestController
@SpringBootApplication
public class SpringTest1Application { // 현재 스프링부트 어플리케이션의 시작점이 되는 클래스

	public static void main(String[] args) {
		SpringApplication.run(SpringTest1Application.class, args);
	}
	
	// SpringApplication 클래스도 컨트롤러 역할 수행 가능
	// => 단, 일반적으로는 별도의 컨트롤러 클래스로 분리
//	@GetMapping("/")
//	public String hello() {
//		return "Hello, World!";
//	}
	

}
