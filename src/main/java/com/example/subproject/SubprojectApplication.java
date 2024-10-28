package com.example.subproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.subproject.repository")
public class SubprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubprojectApplication.class, args);
	}

}
