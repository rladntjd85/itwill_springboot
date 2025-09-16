package com.itwillbs.db.members.controller;

import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwillbs.db.members.dto.MemberDTO;
import com.itwillbs.db.members.service.MemberService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	// ======================================================
	@GetMapping("/regist")
	public String registForm(MemberDTO memberDTO, Model model) {
		model.addAttribute("memberDTO", memberDTO);
		
		return "/members/member_regist_form";
	}
	
	@PostMapping("/regist")
	public String registMember(@ModelAttribute("memberDTO") @Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "/members/member_regist_form";
		}
		
		// MemberService - registMember() 메서드 호출하여 회원가입 처리 요청
		// => 파라미터 : MemberDTO 객체   리턴타입 : void
		memberService.registMember(memberDTO);
		
		// 성공 시 로그인 페이지로 리다이렉트
		// 임시) 메인페이지로 리다이렉트
		return "redirect:/";
	}
	
	// 로그인 폼에서 아이디 기억하기 쿠키 사용을 위해 메서드 파라미터로 @CookieValue 어노테이션을 활용하여 변수 선언
	@GetMapping("/login")
	public String loginForm(@CookieValue(value = "remember-id", required = false) String rememberId, Model model) {
		// 쿠키값 Model 객체에 추가
		model.addAttribute("rememberId", rememberId);
		
		return "/members/member_login_form";
	}
	
	// 웹소켓 로그인 여부 확인
	@ResponseBody
	@GetMapping("/checkLogin")
	public Map<String, Object> checkLogin(Authentication authentication) {
		// 스프링 시큐리티의 Authentication 객체를 주입받아 로그인 상태 판별
		boolean isLoginUser = 
				authentication != null && // 인증 객체 존재(필수)
				authentication.isAuthenticated() && // 로그인 된 사용자(필수)
				!(authentication instanceof AnonymousAuthenticationToken); // 로그인 된 사용자 중 익명 로그인 사용자가 아닐 경우(선택사항)
			
		// Map<String, Object> 타입으로 변환하여 리턴
		return Map.of("isLoginUser", isLoginUser);
	}
	
}














