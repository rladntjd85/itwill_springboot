package com.itwillbs.spring_test3.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Spring JPA 가 관리하는 엔티티 클래스(DB 테이블에 대응하는 클래스) 정의 
@Entity // 현재 클래스를 엔티티 클래스로 선언하는 어노테이션
// => 엔티티 이름은 기본적으로 클래스 이름을 사용하지만 다른 이름으로 변경할 경우
//    @Entity(name = "엔티티명") 형태로 설정 가능 => 주의! 테이블명과 다르다!
//@Table // 매핑할 테이블 지정하는 어노테이션(생략 가능)
// => 테이블 이름은 기본적으로 클래스 이름을 사용하지만 다른 이름으로 변경할 경우
//    @Table(name = "my_member") 형태로 설정 가능
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Member { // 클래스명 Member 와 동일한 이름의 테이블이 DB 에 자동으로 생성됨
	@Id // 현재 필드(id)를 기본키(PK)로 설정(@Id 어노테이션과 필드명(변수명) id 는 무관함 = 달라도 됨)
	@Column(name = "id", updatable = false) // 테이블 컬럼 설정
	// name = "id" : 테이블 내의 컬럼명을 id 로 설정(생략 시 필드명과 동일한 이름의 컬럼이 생성됨)
	// updatable = false : 해당 컬럼은 데이터 저장 후 변경 불가능한 컬럼으로 지정(기본값은 true 로 변경 가능한 컬럼으로 정의됨)
	@GeneratedValue(strategy = GenerationType.AUTO) // @Id 컬럼값 자동 생성 방법을 AUTO 로 지정(JPA 정책에 자동 생성 방법을 위임)
	// => IDENTITY : MySQL 의 AUTO_INCREMENT 방식 사용, SEQUENCE : Oracle 의 방식을 사용
	private Long id; // h2 데이터베이스 기준 BIGINT 타입으로 컬럼 생성
	
	@Column(name = "name", length = 16, nullable = false)
	// 필드명(변수명)과 관계없이 무조건 컬럼명을 name 으로 설정
	// 컬럼 길이는 16 이므로 String 타입에 의해 VARCHAR(16) 등의 형식으로 선언(단, length 속성 생략 시 String 타입 기본 varchar(255) 가 됨)
	// nullable = false 설정 시 해당 컬럼은 NOT NULL 제약조건 설정
	private String name;
	
	// --------------------------------------------------------------
	// 더티체킹 기능을 통해 UPDATE 구문 사용을 위한 메서드 정의 
	// => 메서드 파라미터로 변경할 데이터를 저장할 파라미터 선언
	public void changeName(String name) {
		// 엔티티 클래스 내에서 메서드 파라미터로 전달받은 값을 필드(= 멤버변수)에 저장하면 기존 엔티티에 저장된 값을 변경하며
		// 이 변경된 상태를 JPA 가 감지(= 변경 감지 = 더티체킹(Dirty Checking))하여 트랜잭션 커밋 시점에 DB UPDATE 수행
		this.name = name;
	}
	
}














