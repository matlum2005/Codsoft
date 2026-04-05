# Professional Student Management System

## Overview
Full-stack Student Management System with Spring Boot REST API backend and JavaFX frontend GUI. Features CRUD operations, search, validation, modern UI, exception handling.

## Tech Stack
- **Backend**: Spring Boot 3.2, Java 17, JPA/Hibernate, MySQL, Validation
- **Frontend**: JavaFX 21, FXML, CSS, Java HttpClient
- **Build**: Maven

## Project Structure
```
student-management-system/
├── backend/          # Spring Boot REST APIs
└── frontend-javafx/  # JavaFX GUI App
```

## Prerequisites
1. **Java 17+**
2. **MySQL 8+** running on localhost:3306
3. **Maven 3.8+**
4. **JavaFX SDK** (handled by Maven)

## Setup & Run Instructions

### 1. Database Setup
```sql
CREATE DATABASE studentdb;
```
```
User: root
Password: 8595629739
```

### 2. Backend (API Server)
```bash
cd student-management-system/backend
mvn clean install
mvn spring-boot:run
```
- API runs on `http://localhost:8080/api/students`
- Test: Visit `http://localhost:8080/api/students` in browser

### 3. Frontend (JavaFX GUI)
Open new terminal:
```bash
cd student-management-system/frontend-javafx
mvn clean javafx:run
```
- GUI connects to backend APIs
- Modern table view with add/update/delete/search

## Features Demo
1. **Display All**: Table shows all students
2. **Add**: Click "Add Student" → Fill form → Save
3. **Update**: Select row → "Update" → Modify → Save
4. **Delete**: Select → "Delete" → Confirm
5. **Search**: Enter keyword → "Search" (name/roll/email)
6. **Validation**: Required fields, unique roll no, age 18+
7. **Modern UI**: CSS styled, responsive, alerts

## API Endpoints
```
GET     /api/students           - List all
GET     /api/students/{id}      - Get one
POST    /api/students           - Create
PUT     /api/students/{id}      - Update
DELETE  /api/students/{id}      - Delete
GET     /api/students/search?keyword=abc - Search
```

## Troubleshooting
- **Backend connection**: Ensure MySQL running, DB created
- **CORS**: Enabled for all origins
- **Validation errors**: JSON response with field errors
- **JavaFX modules**: Use `mvn javafx:run`
- **Build errors**: `mvn clean compile`

## Screenshots
(Add screenshots after running)

Professional internship-ready project with clean code, best practices, error handling, modern design!
