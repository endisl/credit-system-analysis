package com.endiluamba.creditmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class CreditmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditmanagerApplication.class, args);
	}

}
