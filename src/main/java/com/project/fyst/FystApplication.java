package com.project.fyst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FystApplication {

	public static void main(String[] args) {
		SpringApplication.run(FystApplication.class, args);
	}

}
