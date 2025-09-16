package com.itwillbs.db.members.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.itwillbs.db.commons.entity.CommonCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class) // 메인클래스에 @EnableJpaAuditing 어노테이션 필요
public class Member implements Serializable { // UserDetails 구현체가 참조하는 사용자 정의 클래스이므로 직렬화 클래스로 선언
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 사용자 아이디는 이메일을 사용
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false, length = 100) // 암호화를 위해 패스워드 길이 100자
	private String passwd;
	
	@Column(nullable = false, length = 20)
	private String name;
	
	@Column(nullable = false, length = 100)
	private String address;
	
	@CreatedDate
	private LocalDate regDate; // 가입일자(엔티티 생성 시 자동 등록)
	
	// --------------------------------------------
	// 사용자(Member)는 사용자권한(CommonCode)과 N:M t관계
	// 그러나, 완충작용을 담당할 MemberRole 엔티티를 정의했으므로 Member 와 MemberRole 은 1:N 관계
	// => @OneToMany 어노테이션으로 1:N 관계 지정
	// 1) mappedBy = "member" 지정 시 현재 엔티티가 연관관계의 주인이 아니므로 상대방의 필드 지정하여 해당 필드 기준으로 매핑 수행
	//    => 주의! 이 때는 @JoinColumn 사용하지 않음!!
	// 2) cascade = CascadeType.ALL 지정 시 부모 저장/삭제되면 자식도 저장/삭제
	// 3) 부모 엔티티와 연관관계가 끊어진 자식 엔티티(고아 객체)를 자동으로 삭제
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MemberRole> roles = new ArrayList<MemberRole>();
	
	// 사용자 권한을 추가하는 addRole() 메서드 정의
	public void addRole(CommonCode role) {
		// MemberRole 객체 생성 시 생성자 파라미터로 현재 엔티티 객체(Member)와 공통코드 엔티티 객체(CommonCode) 전달
		MemberRole memberRole = new MemberRole(this, role);
		// 사용자 권한 목록 객체(List<MemberRole>)에 1개의 권한이 저장된 MemberRole 엔티티 추가
		roles.add(memberRole);
	}
	
	public void test(CommonCode role) {
		// MemberRole 객체 생성 시 생성자 파라미터로 현재 엔티티 객체(Member)와 공통코드 엔티티 객체(CommonCode) 전달
		MemberRole memberRole = new MemberRole(this, role);
		// 사용자 권한 목록 객체(List<MemberRole>)에 1개의 권한이 저장된 MemberRole 엔티티 추가
		roles.add(memberRole);
	}
	
	// 사용자 권한을 제거하는 removeRole() 메서드 정의
	public void removeRole(CommonCode role) {
		// MemberRole 객체 생성 시 생성자 파라미터로 현재 엔티티 객체(Member)와 공통코드 엔티티 객체(CommonCode) 전달
//		MemberRole memberRole = new MemberRole(this, role);
	}
	
	
	
}



















