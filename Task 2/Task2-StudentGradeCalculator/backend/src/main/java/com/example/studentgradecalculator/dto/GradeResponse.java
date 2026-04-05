package com.example.studentgradecalculator.dto;

import com.example.studentgradecalculator.model.StudentResult;

public class GradeResponse {
    private double totalMarks;
    private double percentage;
    private String grade;
    
    public GradeResponse(StudentResult result) {
        this.totalMarks = result.getTotalMarks();
        this.percentage = result.getPercentage();
        this.grade = result.getGrade();
    }
    
    // Getters and Setters
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
}

