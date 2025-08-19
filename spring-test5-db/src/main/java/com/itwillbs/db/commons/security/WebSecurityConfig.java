package com.itwillbs.db.commons.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 기능이 설정되는 클래스
public class WebSecurityConfig {
	
	/*
	 * [ 스프링 시큐리티가 정적 리소스(/static 경로 내의 리소스들)에 대한 보안 필터를 적용하지 않도록 설정하기 ]
	 * @Bean 으로 등록된 메서드 중에서 리턴타입이 WebSecurityCustomizer 인 빈을 찾아 스프링 시큐리티 전역 설정에 사용
	 * => Spring Security 5.4 버전부터 도입된 새로운 방식으로 SecurityFilterChain 에 등록하지 않을 요청을 지정할 수 있도록 해 준다!
	 * => 즉, 여기서 지정하는 경로들은 스프링 시큐리티 보안 필터 자체를 적용받지 않음
	 */
	@Bean
	public WebSecurityCustomizer ignoreStaticResources() { // 메서드 이름 무관
		return (web) -> web.ignoring() // web 객체에 대한 보안 필터 무시
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 일반적인 정적 리소트 경로 모두 지정(css, js, images, error 등)
	}

	// ============================================================================
	// 시큐리티 필터 설정
	// => 메서드 이름 무관하며 반드시 리턴타입이 SecurityFilterChain 타입이어야 함
	// => 메서드 파라미터로 HttpSecurity 타입 지정하여 자동 주입되도록 설정
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// HttpSecurity 객체의 다양한 메서드를 체이닝 형태로 호출하여 스프링 시큐리티 관련 설정을 수행하고
		// 마지막에 build() 메서드 호출하여 HttpSecurity 객체 생성하여 리턴
		return httpSecurity
//				.csrf(csrf -> csrf.disable())
//				.csrf(csrf -> csrf
//					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				)
				// -------------- 요청에 대한 접근 허용 여부 등의 경로에 대한 권한 설정 ------------
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
					.requestMatchers("/").permitAll() // 프로젝트 루트 경로는 누구나 접근 가능하도록 지정
					.anyRequest().authenticated() // 그 외의 모든 요청은 인증된 사용자만 접근 가능하도록 지정
				)
				// -------------- 로그인 처리 ------------
				.formLogin(formLogin -> formLogin
					.loginPage("/members/login") // 로그인 폼으로 사용될 URL 지정
					.loginProcessingUrl("/members/login") // 로그인 폼에서 제출된 데이터를 처리하는 요청 주소(자동으로 POST 방식으로 처리)
					// => 이 때, 지정된 경로는 컨트롤러에서 별도의 매핑이 불필요(단, 로그인 처리 과정에서 부가적인 기능 추가 시 매핑 필요)
					.usernameParameter("email") // 로그인 과정에서 사용할 이름 지정(기본값 "username" => 이메일을 사용하므로 "email" 파라미터명 지정)
					.permitAll() // 로그인 경로 관련 요청 시 모두 허용
				)
				.build();
				
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}




















