package com.fotaleza.fortalezaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class FortalezaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FortalezaApiApplication.class, args);
	}

}
