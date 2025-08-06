package com.itwillbs.db.commons.config;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 스프링 설정 파일로 사용하기 위해 @Configuration 어노테이션 지정(@Component 를 포함하는 어노테이션 = @Service 등과 동일함) 및
// WebMvcConfigurer 인터페이스의 구현체로 정의
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${file.uploadBaseLocation}")
	private String uploadBaseLocation;
	
	// 자원 핸들링을 처리하는 addResourceHandlers() 메서드 오버라이딩
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 클라이언트가 요청하는 가상의 축약 경로를 서버상의 실제 경로로 변환하는 처리
		// 1) 실제 서버상의 디렉토리 지정
		String uploadDir = Paths.get(uploadBaseLocation).toUri().toString();
		
		// 2) URL 패턴에서 "/files/xxx" 형식으로 요청(접근)할 경우 실제 경로(/usr/local/tomcat)를 가리키도록 해당 위치의 리소스 제공
		registry.addResourceHandler("/files/**") // "/files" 로 시작하는 모든 디렉토리에 해당하는 URL 경로 등록
				.addResourceLocations(uploadDir); // 매칭되는 실제 디렉토리 등록
	}
	
}















