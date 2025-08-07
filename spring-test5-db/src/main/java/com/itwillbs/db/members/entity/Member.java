package com.itwillbs.db.members.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Member {
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
	
	
	
}



















