package com.itwillbs.spring_test3.member;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

// 퍼시스턴트 계층 역할을 수행할 Repository 인터페이스 정의
// => JDBC API 와 연결되어 직접적인 DB 작업 수행을 담당하는 역할
// => JpaRepository 인터페이스 상속받아 정의
//    (제네릭타입 지정 시 <엔티티클래스타입, @Id 필드의 데이터타입> 형태로 지정)
//@Component
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	// SELECT * FROM member 문장을 실행할 쿼리 메서드 정의 불필요
	// => JpaRepository 에서 제공하는 findXXX() 메서드 활용하면 SELECT 구문 실행용 메서드 사용 가능
	// => 전체 레코드를 조회하는 메서드는 findAll() 메서드가 제공됨
	// --------------------------------------------------------------------------------------------
	/*
	 * SELECT * FROM member WHERE name = 'X' 문장 실행을 위한 쿼리 메서드 추가 정의
	 * 규칙1) SELECT 구문 실행용 메서드이므로 findByXXX() 이며, XXX 부분은 탐색할 엔티티의 필드명 지정, 파라미터로 검색할 데이터 전달
	 *        ex) Member 엔티티에 String name 필드에 해당하는 데이터를 검색하기 위한 메서드 : findByName(String name)
	 * 규칙2) 검색 결과가 하나의 row 일 경우 리턴타입을 Optional<T> 타입으로 지정하고(T = 엔티티 타입 지정)
	 *        검색 결과가 복수개의 row 일 경우 리턴타입을 List<T> 타입으로 지정     
	 */
	Optional<Member> findByName(String name);

	// deleteXXX() 또는 removeXXX() 메서드로 DELETE 구문의 쿼리메서드 정의 가능
	void deleteByName(String name);

	// 번호와 이름으로 조회
	Optional<Member> findByIdAndName(Long id, String name);
	
}














