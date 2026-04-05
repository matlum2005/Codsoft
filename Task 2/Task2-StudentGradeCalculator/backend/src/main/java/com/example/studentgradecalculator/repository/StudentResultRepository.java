package com.example.studentgradecalculator.repository;

import com.example.studentgradecalculator.model.StudentResult;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface StudentResultRepository extends JpaRepository<StudentResult, Long> {
}

