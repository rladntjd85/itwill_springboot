package com.itwillbs.spring_test3.member;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	/*
	 * A 클래스가 B 클래스에 의존적일 때 B 클래스에 대한 인스턴스를 직접 생성하지 않고,
	 * 의존성 자동 주입(DI = Dependency Injection) 기능을 통해 인스턴스를 전달받아 사용할 수 있다!
	 * 
	 * [ 의존성 주입을 받는 방법 3가지(+ 1가지) ]
	 * 0) 매핑 메서드 파라미터로 주입받을 클래스 타입 매개변수를 선언하는 방법
	 *    => 매핑 메서드가 호출될 때마다 해당 클래스 인스턴스가 생성되어 주입됨(매번 새로운 객체)
	 *    => 단, 이 클래스 타입의 매개변수는 메서드 내에서만 접근 가능하며
	 *       다른 메서드에서는 또 다른 인스턴스를 통해 주입받아 사용해야하므로 매개변수 선언 또 필요
	 *    => 해당 클래스를 평소처럼 정의하면 됨(별다른 추가 작업(어노테이션 설정) 불필요)
	 * -------------------------------------------------------------------------
	 * 만약, 멤버변수(필드) 형태로 주입받을 클래스 타입 변수를 선언하여 메서드마다 공유하는 경우
	 * => 해당 클래스(주입받을 클래스) 정의 시 @Component, @Service 등의 어노테이션을 통해 스프링 빈으로 등록 필수!
	 * => 어노테이션 미지정 시 자동 주입 불가능한 상태로 오류 발생함
	 * 1) 생성자를 통해 의존성 객체를 주입받는 방법
	 *    => 빈으로 등록하지 않았을 경우 오류 메세지 : Parameter 0 of constructor in com.itwillbs.spring_test3.member.MemberController required a bean of type 'com.itwillbs.spring_test3.member.MemberService' that could not be found.)
	 * 2) Setter 메서드를 통해 의존성 객체를 주입받는 방법(Setter 메서드에 @Autowired 어노테이션 필수)
	 *    => 필드에 @Autowired 어노테이션 생략 시 오류 메세지 : java.lang.NullPointerException: Cannot invoke "com.itwillbs.spring_test3.member.MemberService.getMemberList()" because "this.memberService" is null
	 * 3) 멤버변수 선언 시 의존성 객체를 주입받는 방법(멤버변수에 @Autowired 어노테이션 필수)
	 * 4) final 필드 형태로 선언한 후 생성자를 통해 객체 주입
	 *    => 파라미터 생성자를 직접 정의하거나 Lombok 의 @RequiredArgsConstructor 어노테이션을 지정하여 주입
	 */
	
	// 1) 생성자를 통해 의존성 객체를 주입받는 방법
//	private MemberService memberService;
//	public MemberController(MemberService memberService) {
//		super();
//		this.memberService = memberService;
//	}
	
	// 2) Setter 메서드를 통해 의존성 객체를 주입받는 방법(Setter 메서드에 @Autowired 어노테이션 필수)
//	@Autowired
//	private MemberService memberService;
//	
//	public void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}

	// 3) 멤버변수(필드) 선언 시 의존성 객체를 주입받는 방법(멤버변수에 @Autowired 어노테이션 필수)
//	@Autowired
//	private MemberService memberService;
	
	// 4) final 필드 형태로 선언한 후 생성자를 통해 객체 주입
	// => 4-1) 생성자를 직접 정의해서 MemberService 파라미터를 선언하는 방법
	//    4-2) 생성자 직접 정의 대신 Lombok 의 @RequiredArgsConstructor 어노테이션을 지정하는 방법
	private final MemberService memberService;
//	public MemberController(MemberService memberService) {
//		this.memberService = memberService;
//	}
	// => @RequiredArgsConstructor 어노테이션 지정 시 생성자는 정의하면 안됨! (= 중복)

	// ==============================================================================
	@GetMapping("memberList") // /members/memberList 에 대한 요청 매핑
//	public String getAllMembers(MemberService memberService) { // 메서드 호출 시점에 스프링빈으로 등록된 MemberService 인스턴스가 주입됨
	public List<Member> getAllMembers() {
//		MemberService memberService = new MemberService();
		
		// MemberService - getMemberList() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : 없음   리턴타입 : void
//		memberService.getMemberList();
		
		// MemberService - getMemberList() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : 없음   리턴타입 : List<Member>(memberList)
		List<Member> memberList = memberService.getMemberList();
		
		return memberList;
		// @ResponseBody 또는 @RestController 가 적용된 메서드에서 특정 객체를 리턴할 경우
		// 스프링부트에 내장된(정확히는 spring-boot-starter-web 라이브러리 추가 시 포함됨) 기본 JSON 처리 라이브러리인
		// Jackson 을 활용하여 리턴할 객체를 JSON 문자열로 자동 변환하여 리턴
		// => 즉, 리턴할 객체를 Jackson 라이브러리의 메세지 컨버터에 의해 JSON 문자열 형태로 직렬화가 일어남
	}
	
//	@GetMapping("memberInfo") // /members/memberInfo 에 대한 요청 매핑
//	public String getMember(MemberService memberService) { // 메서드 호출 시점에 스프링빈으로 등록된 MemberService 인스턴스가 주입됨
	@GetMapping("memberInfo/{id}") // /members/memberInfo 에 대한 요청 매핑(경로 상에서 값을 바인딩해야할 경우 {} 부분을 대상으로 지정)
	public Member getMember(@PathVariable("id") Long id) { // 경로 상의 바인딩할 데이터를 @PathVariable 어노테이션을 통해 id 변수에 저장
//		MemberService memberService = new MemberService();
		
		// MemberService - getMember() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : 없음   리턴타입 : void
//		System.out.println("id : " + id);
		Member member = memberService.getMember(id);
		
		// 조회 결과인 Member 엔티티를 리턴
		return member;
	}

	// 이름으로 조회
	@GetMapping("memberInfoByName")
	public Member memberInfoByName(@RequestParam(name = "name") String name) {
		// MemberService - getMemberByName() 메서드 호출
		// => 파라미터 : 이름   리턴타입 : Member(엔티티)
		Member member = memberService.getMemberByName(name);
		return member;
	}
	
	// 이름 등록
	@GetMapping("memberRegist")
	public Member memberRegist(@RequestParam(name = "name") String name) {
		// MemberService - registMember() 메서드 호출
		// => 파라미터 : 이름   리턴타입 : Member(member)
		Member member = memberService.registMember(name);
		return member;
	}
	
	// 번호로 삭제
	@GetMapping("memberRemoveById")
	public String memberRemoveById(@RequestParam(name = "id") Long id) {
		// MemberService - removeMemberByName() 메서드 호출
		// => 파라미터 : 번호   리턴타입 : void
		memberService.removeMemberById(id);
		return id + " 삭제 완료!";
	}
	
	// 이름으로 삭제
	@GetMapping("memberRemoveByName")
	public String memberRemoveByName(@RequestParam(name = "name") String name) {
		// MemberService - removeMemberByName() 메서드 호출
		// => 파라미터 : 이름   리턴타입 : void
		memberService.removeMemberByName(name);
		return name + " 삭제 완료!";
	}
	
	// ------------------------------------------
	// 번호, 이름으로 정보 수정
	@GetMapping("memberModify")
	public String memberModify(@RequestParam Map<String, String> params) {
		// MemberService - modifyMember() 메서드 호출
		// => 파라미터 : Map 객체   리턴타입 : void
		memberService.modifyMember(params);
		
		return "";
	}
	
	// ------------------------------------------
	// 번호, 이름으로 검색
	@GetMapping("searchMember")
	public Member searchMember(@RequestParam Map<String, String> params) {
		// MemberService - searchMember() 메서드 호출
		// => 파라미터 : Map 객체   리턴타입 : Member(member)
		Member member = memberService.searchMember(params);
		return member;
	}
	
	
}












