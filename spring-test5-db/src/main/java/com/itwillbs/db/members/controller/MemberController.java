package com.itwillbs.db.members.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@PostMapping("")
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
		
	
}














