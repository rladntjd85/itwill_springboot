package com.itwillbs.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringTest5DbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTest5DbApplication.class, args);
	}

}
