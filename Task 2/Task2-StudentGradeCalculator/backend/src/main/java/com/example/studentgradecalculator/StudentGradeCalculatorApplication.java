package com.example.studentgradecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.studentgradecalculator.model")
@EnableJpaRepositories(basePackages = "com.example.studentgradecalculator.repository")
public class StudentGradeCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentGradeCalculatorApplication.class, args);
    }
}

