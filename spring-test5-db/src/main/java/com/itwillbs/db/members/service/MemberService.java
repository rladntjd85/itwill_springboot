package com.itwillbs.db.members.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwillbs.db.commons.entity.CommonCode;
import com.itwillbs.db.commons.enums.MemberRoleType;
import com.itwillbs.db.commons.service.CommonCodeService;
import com.itwillbs.db.members.dto.MemberDTO;
import com.itwillbs.db.members.entity.Member;
import com.itwillbs.db.members.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final CommonCodeService commonCodeService;

	public MemberService(MemberRepository memberRepository, CommonCodeService commonCodeService) {
		this.memberRepository = memberRepository;
		this.commonCodeService = commonCodeService;
	}

	// 회원 등록 요청
	public void registMember(@Valid MemberDTO memberDTO) {
		// 이메일 주소 중복 확인 처리
		validateDuplilcateMember(memberDTO);
		
		// 패스워드 단방향 암호화(평문 -> 암호문) 처리
		// => BCryptPasswordEncoder 객체의 encode() 메서드 활용
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
		
		// 기본적으로 일반 사용자로 가입하기 위해 공통코드 테이블의 일반 사용자 값을 권한 값으로 설정
		// => 공통 코드 테이블(common_code)에서 해당 계정 유형을 조회하여 설정 필요
		// 1) CommonCodeService - getMemberRole() 메서드 호출하여 공통코드 테이블에서 일반 사용자 코드 조회
//		CommonCode commonCode = commonCodeService.getMemberRole(MemberRoleType.USER);
		CommonCode commonCode = commonCodeService.getMemberRole("USER");
		System.out.println("사용자 권한 코드 : " + commonCode.getCodeGroup() + ", " + commonCode.getCommonCode());
		
		// 2) MemberDTO -> Member 엔티티로 변환
		Member member = memberDTO.toEntity();
		
		// 3) Member 엔티티에 공통코드(사용자 계정 유형) 추가
		member.addRole(commonCode); // 임시로 test() 메서드 사용 -> addRole() 인식 안 됨
		
		// MemberRepository - save() 메서드 호출하여 등록 요청
		Member resultMember = memberRepository.save(member);
		
		if(resultMember == null) {
			throw new IllegalStateException("회원 등록 실패!");
		}
		
	}
	
	// ====================================================================
	// 아이디로 사용되는 컬럼값(이메일) 주소 중복 확인
	private void validateDuplilcateMember(MemberDTO memberDTO) {
		// MemberRepository - findByEmail() 메서드 호출하여 사용자 아이디로 사용되는 이메일 주소로 조회 요청
//		memberRepository.findByEmail(memberDTO.getEmail())
//			.orElseThrow(() -> new EntityNotFoundException("이미 가입된 회원입니다!"));
		// => 주의! 조회 결과가 없을 경우 예외 발생함

		// 조회결과가 있을 경우에만 예외 발생시키기
		Optional<Member> member = memberRepository.findByEmail(memberDTO.getEmail());
		
		if(member.isPresent()) { // => 조회결과가 있을 경우 예외 발생
			System.out.println("이미 가입된 회원입니다!");
			throw new EntityNotFoundException("이미 가입된 회원입니다!");
		} else {
			System.out.println("가입 가능한 회원입니다!");
		}
			
		// 위의 코드를 orElseThrow() 메서드처럼 처리하기
		// => findByEmail() 메서드 호출 결과(m = 조회된 Member 엔티티)가 존재하면(ifPresent()) 예외 발생시키기
//		memberRepository.findByEmail(memberDTO.getEmail())
//			.ifPresent(m -> new EntityNotFoundException("이미 가입된 회원입니다2222!"));
	}
	
}















