package com.example.studentgradecalculator.service;

import com.example.studentgradecalculator.model.StudentResult;
import com.example.studentgradecalculator.repository.StudentResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentResultService {

	@Autowired
	private StudentResultRepository repository;
    
    public StudentResult calculateAndSave(List<Double> marks) {
        double totalMarks = marks.stream().mapToDouble(Double::doubleValue).sum();
        double percentage = totalMarks / marks.size();
        String grade = calculateGrade(percentage);
        
        StudentResult result = new StudentResult();
        result.setTotalMarks(totalMarks);
        result.setPercentage(percentage);
        result.setGrade(grade);
        
        return repository.save(result);
    }
    
    public List<StudentResult> getAllResults() {
        return repository.findAll();
    }
    
    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        return "Fail";
    }
}

