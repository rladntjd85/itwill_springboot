package com.itwillbs.db.commons.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 공통코드 관리하는 엔티티
@Entity
// 테이블 제약조건 설정 시 두 개의 컬럼의 조합이 유니크인 조건 설정(code_group 컬럼 + common_code 컬럼 = 유니크이면 OK)
//@Table(name = "common_code", uniqueConstraints = @UniqueConstraint(columnNames = {"code_group", "common_code"}))
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code_group", "common_code"}))
@Getter
@Setter
@ToString
public class CommonCode implements Serializable { // UserDetails 구현체가 참조하는 사용자 정의 클래스이므로 직렬화 클래스로 선언
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "code_group", nullable = true, length = 20)
	private String codeGroup; // 상위공통코드(공통코드그룹)
	
	@Column(name = "common_code", nullable = false, length = 50)
	private String commonCode; // 공통코드
	
	@Column(name = "common_code_name", nullable = false, length = 20)
	private String commonCodeName; // 공통코드명(코드를 한국어로 표기하기 위함)
	
	private String description; // 공통코드 상세설명
	
}





















