package com.itwillbs.db.commons.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwillbs.db.commons.util.ModelMapperUtils;
import com.itwillbs.db.members.dto.MemberLoginDTO;
import com.itwillbs.db.members.entity.Member;
import com.itwillbs.db.members.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// 스프링 시큐리티에서 인증 처리(로그인 등)를 수행하는 클래스 정의
// => UserDetailsService(스프링 시큐리티에서 인증 시 사용자 정보(UserDetails 객체로 관리됨)를 불러오는 핵심 인터페이스)를 상속받아 정의
// => UserDetailsService 인터페이스를 구현한 구현체는 인증에 사용되는 사용자 정보를 DB 로부터 직접 조회 가능
@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository; 
	// --------------------------------------------------------------
	// 스프링 시큐리티에서 사용자 정보 조회에 사용될 loadUserByUsername() 메서드 오버라이딩
	// => 파라미터로 전달받은 문자열(username)은 실제 조회에 사용할 유니크한 정보여야 한다! (ex. 사용자 아이디, 이메일, 전화번호 등)
	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // 파라미터명 변경 가능
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		log.info("▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ 사용자 정보 조회 시작!");
		log.info("▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ 사용자 정보 조회에 사용될 username : " + email);
		
		// email 을 사용하여 Member 엔티티 조회
//		Member member = memberRepository.findByEmail(email)
//				.orElseThrow(() -> new UsernameNotFoundException(email + " : 사용자 조회 실패!"));
//		log.info("▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ 사용자 정보 : " + member);
		// => Member 엔티티의 List<MemberRole> 객체에 대한 지연 로딩이 설정되어 있는데(MemberRole - Memer 타입에 대한 지연로딩 설정됨)
		//    이 Member 엔티티에 접근하려 할 경우 트랜잭션 경계 밖에서 현재 코드들이 실행되므로 지연 로딩 필드 접근이 불가능하다!
		// => List<MemberRole> 타입 정보(함께 연관되어 있는 CommonCode 엔티티까지)를 실제 조회될 수 있도록 해야한다! (JOIN FETCH = 즉시 로딩)
		Member member = memberRepository.findByEmailWithRoles(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " : 사용자 조회 실패!"));
		log.info("▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ 사용자 정보 : " + member);
		
		// 조회된 결과를 스프링 시큐리티가 자체적으로 제공하는 Users 객체 형태로 그대로 리턴하거나
		// Users 클래스가 구현한 부모타입인 UserDetails 타입 객체를 리턴해도 됨
		// 단, 별도의 클래스를 UserDetails 인터페이스 구현체로 정의했을 경우(ex. MemberLoginDTO) 해당 객체를 그대로 리턴해도 됨
		// (MemberLoginDTO 객체를 리턴 시 UserDetails 타입으로 업캐스팅되어 리턴됨)
		// Member 엔티티 -> MemberLoginDTO 객체로 변환하여 리턴하기
		MemberLoginDTO memberLoginDTO = ModelMapperUtils.convertObjectByMap(member, MemberLoginDTO.class);
//		System.out.println("memberLoginDTO.getUsername() : " + memberLoginDTO.getUsername());
//		log.info("▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ memberLoginDTO.getPassword() : " + memberLoginDTO.getPassword());
//		System.out.println("memberLoginDTO.getAuthorities() : " + memberLoginDTO.getAuthorities());
//		System.out.println("memberLoginDTO.getEmail() : " + memberLoginDTO.getEmail());
//		log.info("▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ memberLoginDTO.getPasswd() : " + memberLoginDTO.getPasswd());
//		System.out.println("memberLoginDTO.getName() : " + memberLoginDTO.getName());
//		System.out.println("memberLoginDTO.getRoles() : " + memberLoginDTO.getRoles());
//		System.out.println("memberLoginDTO : " + memberLoginDTO);
		
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		log.info("▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ 패스워드 검증 : " + passwordEncoder.matches(memberLoginDTO.getPasswd(), memberLoginDTO.getPassword()));
		
		
		return memberLoginDTO; // MemberLoginDTO -> UserDetails 타입으로 업캐스팅되어 리턴됨(리턴타입 : UserDetails)
	}

}

















