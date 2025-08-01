package com.itwillbs.spring_test3.member;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Component // 스프링 빈으로 등록하는 공통 어노테이션
@Service // 스프링 빈으로 등록하는 어노테이션이자, 서비스 역할(= 비즈니스 로직 처리)을 수행하는 클래스는 의미의 어노테이션
public class MemberService {
	// 스프링 빈으로 등록된 MemberRepository 객체 자동 주입 설정
	@Autowired
	private MemberRepository memberRepository;
	
	public List<Member> getMemberList() {
		System.out.println("MemberService - getMemberList() 호출됨!");
		
		// 전체 member 테이블(정확히는 Member 엔티티)을 조회 작업 요청
		// => JPA 에서 데이터베이스 작업을 실질적으로 처리하는 Repository 의 메서드를 호출하여 데이터 조회 요청
		// => 기본적인 전체 테이블 조회 구문(SELECT * FROM xxx) 을 수행하는 메서드는 별도의 쿼리 메서드 정의 없이
		//    기본으로 제공하는 findAll() 메서드 호출하여 목록 조회 및 데이터 리턴 가능
		return memberRepository.findAll();
		// => SELECT * FROM member 구문을 실행하는 메서드 호출
		// => findAll() 메서드 리턴타입이 기본적으로 List<T> 타입인데
		//    MemberRepository 정의 시 extends JpaRepository<Member, Long> 형태로 상속을 표현했는데
		//    이 때, 제네릭타입 첫번째인 엔티티 클래스 타입 Member 가 T 부분에 해당하는 타입으로 적용된다!
		// => 따라서, 전체 레코드 조회 결과가 List<T> 타입이 아닌 List<Member> 타입으로 변경되어 해당 타입 객체가 리턴됨
	}

	public Member getMember(Long id) {
		System.out.println("MemberService - getMember() 호출됨!");
		
		// member 테이블에서 id 값이 일치하는 1개 레코드 조회(SELECT * FROM member WHERE id = ?)
		// => JpaRepository 를 통해 findByXXX() 메서드가 제공됨(특정 대상을 기준으로 레코드 검색하는 용도의 메서드)
		// => @Id 가 붙은 필드(Long id)를 기준으로 레코드 검색하기 위해 findById() 메서드 호출
		//    (주의! id 컬럼명이 아닌 @Id 어노테이션이 붙은 컬럼이라는 의미)
//		return memberRepository.findById(id).get(); 
		// => findById() 메서드 리턴타입은 Optional<T> 타입이며, 현재 T 타입이 Member 엔티티 클래스 타입이므로 Optional<Member> 타입 리턴됨
		//    다만, 실제 리턴해야하는 객체는 Member 엔티티 타입이므로 findById() 메서드 리턴 객체(Optional 타입)의 get() 메서드 호출 필요
		// => 주의! 조회 결과가 없을 경우 조회된 엔티티가 없으며, 이 때 get() 메서드가 호출되면 예외 발생함
		//    (java.util.NoSuchElementException: No value present)
		//    따라서, Optinal 클래스를 활용하여 null 타입을 안전하게 처리하는 방법으로 회피 가능함
		
		// Optional<Member> 타입을 리턴받아 null 이 아닐 경우에만 조회 결과를 그대로 리턴하고 
		// null 일 경우 Member 엔티티를 리턴하지 않도록 처리하는 방법 예시 
//		Optional<Member> optionalMember = memberRepository.findById(id);
//		
//		if(optionalMember.isEmpty()) { // 조회 결과가 없을 경우(Optinal<Member> 타입 객체가 비어있을 경우) <-> 반대는 isPresent()
//			System.out.println("조회 결과 없음!");
//			return null;
//		}
//		
//		System.out.println("조회 결과 있음!");
//		return optionalMember.get();
		
		// ------------------------------------------------------------------
		// Optioncal 객체의 orElseThrow() 메서드 활용하여
		// 조회 결과가 없을 경우 객체가 null 되며, 이 때, 특정 예외를 외부로 던지는 추가적인 행위가 가능
		// 단, 조회 결과가 있을 경우 orElseThrow() 메서드 부분은 생략되고 Optional 객체의 get() 메서드를 통해 실제 엔티티를 리턴
//		return memberRepository.findById(id)
//				.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다!")); // 예외 발생
		
		// orElse() 메서드 사용 시 예외를 외부로 던지는 대신 새로운 엔티티 객체를 생성하여 리턴할 수 있음
		// => 즉, 예외가 발생하지 않고 빈 엔티티 객체를 외부로 던져 안전하게 처리할 수 있음
		return memberRepository.findById(id)
				.orElse(new Member()); // 조회 결과가 없을 경우 다른 Member 엔티티 객체를 생성하여 리턴할 수 있음(빈 Member 객체)
		
		
	}

	// 이름으로 Member 엔티티 검색
	public Member getMemberByName(String name) {
		// MemberRepository - findByXXX() 메서드가 제공되므로 findByName() 메서드를 호출해야하지만
		// 기본키 컬럼 외의 나머지 컬럼은 테이블 설계에 따라 완전 다른 이름이 사용될 수 있으므로 모든 이름의 메서드가 제공되지 않는다!
		// => MemberRepository 인터페이스에 원하는 데이터 조회를 위한 메서드를 추가로 정의해야한다!
		return memberRepository.findByName(name)
				.orElse(new Member());
	}

	// =============================================================
	// Member 엔티티를 통해 이름 저장 - INSERT
	// => 주의! INSERT, UPDATE, DELETE 작업등의 메서드는 상단에 @Transactional 어노테이션을 통해 트랜잭션 적용 필요
	@Transactional
	public Member registMember(String name) {
		// INSERT 수행에 사용될 엔티티 객체 생성
		// => 엔티티 객체 생성 시 PK 값으로 사용되는 id값(@Id 어노테이션이 붙은 id 필드)은 null 값 전달 => 자동으로 부여되기 때문
		//    단, 기본 생성자 사용할 경우 id 값을 제외한 다른 데이터만 저장하면 됨
		// => 주의! data.sql 파일 등을 통해 더미데이터가 저장되어 있을 경우 PK 값 중복으로 인한 오류 발생할 수 있음!
		//    (따라서, 옵션을 사용하여 실행되지 않도록 하거나 data.sql 이 아닌 다른 이름으로 변경하기)
		Member member = new Member();
		member.setName(name);
		// --------------------------
		// MemberRepositoryh - save() 메서드 호출하여 INSERT 작업 요청
		// => 파라미터 : 엔티티 객체   리턴타입 : 엔티티 객체에 해당하는 타입 객체(성공 시 해당 엔티티 리턴됨)
		return memberRepository.save(member);
	}

	// 번호(PK)로 Member 엔티티를 통해 DELETE
	@Transactional
	public void removeMemberById(Long id) {
		// [ 1번방법. deleteById() 메서드를 통해 id 값을 전달하여 삭제 처리 ]
//		memberRepository.deleteById(id);
		// => 리턴타입이 void 이므로 삭제 여부 확인이 불가능(별도의 조회 작업 추가 필요)
		
		// [ 2번방법. 삭제할 엔티티를 먼저 조회한 후 delete() 메서드를 통해 엔티티 객체를 전달하여 삭제 처리 ]
		// 1) 삭제할 id 에 대한 엔티티 조회(없을 경우 예외 던지기)
		Member member = memberRepository.findById(id)
							.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다!"));
		// 2) MemberRepository - delete() 메서드 호출하여 레코드 삭제 요청
		//    => 파라미터 : 삭제할 엔티티(엔티티 조회가 선행되어야 한다!)		
		memberRepository.delete(member);
	}
	
	// 이름으로 Member 엔티티를 통해 DELETE
	@Transactional
	public void removeMemberByName(String name) {
		// [ 1번방법. deleteByName() 메서드를 통해 id 값을 전달하여 삭제 처리 ]
//		memberRepository.deleteByName(name);
		
		// [ 2번방법. 삭제할 엔티티를 먼저 조회한 후 delete() 메서드를 통해 엔티티 객체를 전달하여 삭제 처리 ]
		// 1) 삭제할 name 에 대한 엔티티 조회
		Member member = memberRepository.findByName(name)
							.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다!"));
		
		// 2) MemberRepository - delete() 메서드 호출하여 레코드 삭제 요청
		//    => 파라미터 : 삭제할 엔티티(엔티티 조회가 선행되어야 한다!)		
		memberRepository.delete(member);
	}

	// ======================================================
	// 정보 수정(번호에 해당하는 이름을 수정)
	@Transactional
	public void modifyMember(Map<String, String> params) {
		// 정보 수정에 필요한 엔티티 조회(id 값으로 조회)
		// => Map 객체의 모든 값이 String 타입이므로 Long 타입으로 변환 후 전달
		Member member = memberRepository.findById(Long.parseLong(params.get("id")))
				.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다!"));
//		System.out.println(member);
		
		// UPDATE 작업 수행은 Repository 의 쿼리 메서드 호출이 아닌 엔티티 클래스의 필드값 변경 메서드 호출을 통해 변경해야함
		// => Member 클래스(엔티티)내에 changeName() 등의 메서드를 정의하여 엔티티의 필드값을 변경하는 코드 작성하면
		//    해당 메서드 호출 시점에 JPA 더티체킹(Dirty Checking = 엔티티의 값이 변경된 것을 자동으로 감지)에 의해
		//    변경된 값에 대한 DB UPDATE 가 자동으로 수행됨
		// => 주의! 새로운 엔티티가 아닌 기존에 조회 완료된 엔티티를 대상으로 changeName() 메서드 호출해야한다!
		member.changeName(params.get("name"));
	}

	// ======================================================
	// 번호와 이름에 해당하는 레코드 검색
	public Member searchMember(Map<String, String> params) {
		// 번호(id)와 이름(name) 컬럼이 모두 일치해야하므로 findByIdAndName() 메서드 추가 정의 필요
		// => 주의! JpaRepository 메서드 호출 시 Map 객체 등을 전달하면 속성 구분이 불가능하므로 각각의 데이터를 따로 전달
		Long id = Long.parseLong(params.get("id"));
		String name = params.get("name");
		
		return memberRepository.findByIdAndName(id, name)
				.orElse(new Member());
	}


}























