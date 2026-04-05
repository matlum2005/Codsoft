package com.example.studentgradecalculator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CalculateRequest {
    @NotNull
    private List<@Min(0) @Max(100) Double> marks;
    
    // Getters and Setters
    public List<Double> getMarks() {
        return marks;
    }
    
    public void setMarks(List<Double> marks) {
        this.marks = marks;
    }
}

