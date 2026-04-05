package com.example.studentgradecalculator.controller;

import com.example.studentgradecalculator.dto.CalculateRequest;
import com.example.studentgradecalculator.dto.GradeResponse;
import com.example.studentgradecalculator.model.StudentResult;
import com.example.studentgradecalculator.service.StudentResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentResultController {

	@Autowired
	private StudentResultService service;
    
    @PostMapping("/calculate")
    public GradeResponse calculate(@Valid @RequestBody CalculateRequest request) {
        StudentResult result = service.calculateAndSave(request.getMarks());
        return new GradeResponse(result);
    }
    
    @GetMapping("/all")
    public List<StudentResult> getAllResults() {
        return service.getAllResults();
    }
}

