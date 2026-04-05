package com.example.studentgradecalculator.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_results")
public class StudentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "total_marks", nullable = false)
    private double totalMarks;
    
    @Column(nullable = false)
    private double percentage;
    
    @Column(nullable = false)
    private String grade;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

