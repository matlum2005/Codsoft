package com.studentmgmt.service;

import com.studentmgmt.entity.Student;
import com.studentmgmt.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByRollNumber(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber);
    }

    @Transactional
    public Student saveStudent(@Valid Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> searchStudents(String keyword) {
        return studentRepository.findAll().stream()
                .filter(s -> s.getName().toLowerCase().contains(keyword.toLowerCase())
                        || s.getRollNumber().toLowerCase().contains(keyword.toLowerCase())
                        || s.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}
