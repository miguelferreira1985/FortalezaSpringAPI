package com.fotaleza.fortalezaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.fotaleza.fortalezaapi"
})
@EntityScan(basePackages = {
        "com.fotaleza.fortalezaapi.model"
})
@EnableJpaRepositories(basePackages = {
        "com.fotaleza.fortalezaapi.repository"
})
public class FortalezaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FortalezaApiApplication.class, args);
	}

}
