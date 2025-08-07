package com.itwillbs.db.members.service;

import org.springframework.stereotype.Service;

import com.itwillbs.db.members.dto.MemberDTO;
import com.itwillbs.db.members.entity.Member;
import com.itwillbs.db.members.repository.MemberRepository;

import jakarta.validation.Valid;

@Service
public class MemberService {
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	// 회원 등록 요청
	public void registMember(@Valid MemberDTO memberDTO) {
		// 이메일 주소 중복 확인 처리 등.... 생략
		
		// 패스워드 단방향 암호화 처리(생략)
		
		
		// MemberDTO -> Member 엔티티로 변환
		Member member = memberDTO.toEntity();
		
		// MemberRepository - save() 메서드 호출하여 등록 요청
		Member resultMember = memberRepository.save(member);
		
		if(resultMember == null) {
			throw new IllegalStateException("회원 등록 실패!");
		}
		
	}
	
	
}















