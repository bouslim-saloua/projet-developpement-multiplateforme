package com.emsi.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.emsi.quiz.service", "com.emsi.quiz.controller", "com.emsi.quiz.service.impl", "com.emsi.quiz.config","com.emsi.quiz.security"})
@EntityScan(basePackages = "com.emsi.quiz.entity")
@EnableJpaRepositories("com.emsi.quiz.repository")

public class QuizBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizBackendApplication.class, args);
    }
}
