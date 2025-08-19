package com.itwillbs.db.members.entity;

import com.itwillbs.db.commons.entity.CommonCode;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 회원 권한(= 공통코드 테이블의 상위코드명 "MEMBER_ROLE")을 관리하는 엔티티
// => Member 엔티티와 1:N 관계(Member(1) : MemberRole(N))
// => CommonCode 엔티티와 1:N 관계(CommonCode(1) : MemberRole(N))
@Entity
@Table(name = "member_role")
@Getter
@Setter
@NoArgsConstructor
public class MemberRole {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// Member 엔티티와 연관관계 설정
	@ManyToOne(fetch = FetchType.LAZY) // 지연로딩
	@JoinColumn(name = "member_id", nullable = false) // member_role 테이블에 member_id 컬럼을 사용하여 Member 엔티티를 참조(FK 설정)
	private Member member;
	
	// CommonCode 엔티티와 연관관계 설정
	@ManyToOne(fetch = FetchType.LAZY) // 지연로딩
	@JoinColumn(name = "member_role_id", nullable = false) // common_code 테이블에 member_role_id 컬럼을 사용하여 CommonCode 엔티티를 참조(FK 설정)
	private CommonCode role;
	
	// id 를 제외한 Member, CommonCode 엔티티를 초기화하는 생성자 정의
	public MemberRole(Member member, CommonCode role) {
		this.member = member;
		this.role = role;
	}
	
}




















