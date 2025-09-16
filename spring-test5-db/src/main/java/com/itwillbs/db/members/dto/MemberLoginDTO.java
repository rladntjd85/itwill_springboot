package com.itwillbs.db.members.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.itwillbs.db.members.entity.MemberRole;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

// 로그인 인증에 사용될 인증 전용 DTO 클래스 정의(선택사항) => MemberDTO 그대로 사용도 가능함
// => 자동으로 현재 클래스가 인증용으로 사용되려면 반드시 스프링 시큐리티에서 제공하는 UserDetails 인터페이스를 구현해야함
// => 주의! 스프링 시큐리티에서 필요에 따라 세션을 직렬화/역직렬화 해야하므로 인증 객체를 Serializable 인터페이스 구현체로 선언해야함
//    또한, 인증 객체가 참조하는 다른 사용자 클래스가 있을 경우 해당 클래스들도 모두 Serializable 인터페이스 구현체로 선언해야함
@Getter
@Setter
@ToString
@Log4j2
public class MemberLoginDTO implements UserDetails, Serializable {
	private String email;
	private String passwd;
	private String name;
	private List<MemberRole> roles; // 사용자 권한 목록 => MemberRole(사용자 클래스)도 직렬화 클래스로 선언해야함
	// -----------------------------------------------
	// 필수 오버라이딩 메서드
	// 1) 사용자의 권한 목록을 리턴하는 메서드
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 컬렉션(List, Set 등) 을 또다른 컬렉션 형태로 변환하여 리턴
		return roles.stream() // 컬렉션 요소를 스트림으로 변환
				.map(role -> new SimpleGrantedAuthority(role.getRole().getCommonCode())) // 스트림 각 요소를 다른 객체로 변환(스프링시큐리티가 관리하는 권한 객체(SimpleGrantedAuthority)로 변환)
				.collect(Collectors.toList()); // 최종적으로 변환된 SimpleGrantedAuthority 객체들을 List 객체로 모아줌
	}
	
	// 2) 사용자 이름(= 아이디 역할)을 리턴하는 메서드
	//    현재 사용자 이름을 이메일로 대체하여 사용하므로 이메일 주소 리턴
	@Override
	public String getUsername() {
		return email;
	}
	
	// 3) 사용자 패스워드를 리턴하는 메서드
	@Override
	public String getPassword() {
		return passwd;
	}
	
	// -------------------------------------
	// 선택적 오버라이딩 메서드
	// 4) 계정 만료 여부를 리턴
	@Override
	public boolean isAccountNonExpired() {
		// 실제 계정 만료 여부 확인하는 서비스 로직 필요
		return true; // 계정이 만료되지 않았다는 의미로 임시값 true 리턴
	}
	
	// 5) 계정 잠금 여부를 리턴
	@Override
	public boolean isAccountNonLocked() {
		// 실제 계정 잠금 여부 확인하는 서비스 로직 필요
		return true; // 계정이 잠기지 않았다는 의미로 임시값 true 리턴	
	}
	
	// 6) 패스워드 기간 만료 여부 리턴
	@Override
	public boolean isCredentialsNonExpired() {
		// 실제 패스워드 기간 만료 여부 확인하는 서비스 로직 필요
		return true; // 패스워드 기간 만료되지 않았다는 의미로 임시값 true 리턴	
	}
	
	// 7) 계정 사용 가능(활성화) 여부 리턴
	@Override
	public boolean isEnabled() {
		// 실제 계정 사용 가능 여부 확인하는 서비스 로직 필요
		return true; // 계정 사용 가능하다는 의미로 임시값 true 리턴	
	}
	
}


















