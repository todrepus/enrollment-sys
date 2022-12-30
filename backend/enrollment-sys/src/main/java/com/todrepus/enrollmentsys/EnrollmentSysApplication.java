package com.todrepus.enrollmentsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EnrollmentSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnrollmentSysApplication.class, args);
	}

}
