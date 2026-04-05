package com.studentmgmt.model;

public class Student {
    private Long id;
    private String name;
    private String rollNumber;
    private String email;
    private Integer age;

    public Student() {}

    public Student(Long id, String name, String rollNumber, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.email = email;
        this.age = age;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    @Override
    public String toString() {
        return name + " (" + rollNumber + ")";
    }
}
