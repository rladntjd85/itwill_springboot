package com.itwillbs.db.commons.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

// 스프링시큐리티 로그인 성공 시 작업을 처리하는 핸들러 정의(AuthenticationSuccessHandler 인터페이스 구현체로 정의)
// 별도로 스프링 빈으로 등록할 필요 없음
@Log4j2
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	// 로그인 성공 시 별도의 추가 작업(ex. 아이디 기억하기, 읽지 않은 메세지 확인 작업 등)을 onAuthenticationSuccess() 메서드 오버라이딩
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 로그인에 사용된 요청 정보가 HttpServletRequest, 응답 정보가 HttpServletResponse 객체로 전달되고
		// 로그인 한 사용자의 인증 정보가 Authentication 타입 객체로 전달됨
//		log.info(">>>>>>>>>>>> authentication.getName() : " + authentication.getName()); // 사용자명(username = 현재 email)
//		log.info(">>>>>>>>>>>> authentication.getAuthorities() : " + authentication.getAuthorities()); // 권한 목록
//		log.info(">>>>>>>>>>>> authentication.getDetails() : " + authentication.getDetails()); // 사용자 IP 주소, 세션 아이디
//		log.info(">>>>>>>>>>>> authentication.getPrincipal() : " + authentication.getPrincipal()); // 인증 객체(UserDetails 또는 상속받은 구현체)
		// ======================================================================
		// 만약, 특정 정보를 세션 객체에 저장해야할 경우
//		HttpSession session = request.getSession(); // HttpServletRequest 객체로부터 세션 객체 가져오기
////		HttpSession session = request.getSession(false); // 기존 세션 객체가 존재할 경우 새로운 세션 객체를 생성하지 않도록 지정
//		session.setAttribute("userDetails", authentication.getDetails());
		// ======================================================================
		// 아이디 기억하기 체크박스 체크 시 쿠키에 아이디(email) 저장
		String rememberId = request.getParameter("remember-id"); // 체크박스 파라미터값 가져오기
//		log.info(">>>>>>>>>>>> rememberId : " + rememberId); // null 또는 "on"
		
		// 쿠키 생성 공통 코드
		// 쿠키 객체 생성하여 사용자명(email)을 "remember-id" 로 저장
//		Cookie cookie = new Cookie("remember-id", authentication.getName());
		// 만약, 한글 등의 값이 포함되어 있는 문자열일 경우 인코딩 필요
		Cookie cookie = new Cookie("remember-id", URLEncoder.encode(authentication.getName(), "UTF-8"));
		cookie.setPath("/"); // 애플리케이션 내에서 모든 경로 상에서 쿠키 사용이 가능하도록 설정
		
		
		// 체크박스 파라미터가 존재하고, 해당 파라미터값이 "on" 일 경우 판별
		if(rememberId != null && rememberId.equals("on")) { // 아이디 기억하기 체크박스 체크 시
			cookie.setMaxAge(60 * 60 * 24 * 7); // 쿠키 유효기간 설정(7일)
//			cookie.setPath("/"); // 애플리케이션 내에서 모든 경로 상에서 쿠키 사용이 가능하도록 설정
//			response.addCookie(cookie); // 응답 객체에 쿠키 추가
		} else { // 아이디 기억하기 체크박스 체크 해제 시
//			Cookie cookie = new Cookie("remember-id", URLEncoder.encode(authentication.getName(), "UTF-8"));
			cookie.setMaxAge(0); // 쿠키 유효기간 0초로 설정 = 쿠키 삭제
//			cookie.setPath("/"); // 애플리케이션 내에서 모든 경로 상에서 쿠키 사용이 가능하도록 설정
//			response.addCookie(cookie); // 응답 객체에 쿠키 추가
		}
		
		response.addCookie(cookie); // 응답 객체에 쿠키 추가
		
		response.sendRedirect("/"); // 메인페이지로 리다이렉트
	}

}


















