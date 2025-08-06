package com.itwillbs.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.EntityListeners;

@SpringBootApplication
@EnableJpaAuditing // @EntityListeners(AuditingEntityListener.class) 어노테이션을 통한 자동 등록 기능을 처리하기 위해 추가 필수!
public class SpringTest5DbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTest5DbApplication.class, args);
	}

}
