# 🎯 Student Grade Calculator - Full Stack Project

## 🛠 Tech Stack
- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Spring Boot 3.2.0 (Java 17), JPA/Hibernate
- **Database**: MySQL 8+

## 📁 Folder Structure
```
Task2-StudentGradeCalculator/
├── frontend/           # Static web files
│   ├── index.html
│   ├── style.css
│   └── script.js
└── backend/            # Spring Boot Maven project
    ├── pom.xml
    └── src/main/java/...
```

## 🚀 Step-by-Step Setup & Run Guide

### 1. Prerequisites
```
- Java 17+ installed
- Maven 3.8+ installed  
- MySQL 8+ installed & running
- IDE (VSCode/IntelliJ) recommended
```

### 2. Database Setup
1. Create database:
```sql
CREATE DATABASE student_db;
```

2. Connect to `student_db` and run (optional, since ddl-auto=update):
```sql
CREATE TABLE IF NOT EXISTS student_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_marks DOUBLE NOT NULL,
    percentage DOUBLE NOT NULL,
    grade VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

3. Update `backend/src/main/resources/application.properties`:
   - Set `spring.datasource.password=your_actual_mysql_password`
   - Use your MySQL username (default: root)

### 3. Backend Setup & Run
```bash
cd "c:/Users/DELL/Desktop/Task 2/Task2-StudentGradeCalculator/backend"
mvn clean install
mvn spring-boot:run
```
- Backend runs on `http://localhost:8080`
- CORS enabled for frontend

### 4. Frontend Setup & Run
```bash
cd "c:/Users/DELL/Desktop/Task 2/Task2-StudentGradeCalculator/frontend"
# Option 1: Open index.html directly in browser
# Option 2: Use Live Server extension in VSCode
# Option 3: Any simple HTTP server: npx serve .
```

### 5. API Testing (Postman/cURL)

**POST Calculate Grade** `http://localhost:8080/api/student/calculate`
```bash
curl -X POST http://localhost:8080/api/student/calculate \
  -H "Content-Type: application/json" \
  -d '{"marks": [85, 92, 78, 95]}'
```
Response:
```json
{
  "totalMarks": 350,
  "percentage": 87.5,
  "grade": "A"
}
```

**GET All Results** `http://localhost:8080/api/student/all`
```bash
curl http://localhost:8080/api/student/all
```

### 6. Test Full Flow
1. Start backend ✅
2. Open frontend in browser
3. Add subjects, enter marks (0-100)
4. Click \"Calculate & Save\"
5. View result + history table
6. Refresh history

## 🧪 Features Verified
- ✅ Dynamic subject inputs (add/remove)
- ✅ Client-side validation (0-100)
- ✅ Backend grade calculation (A+/A/B/C/Fail)
- ✅ Database persistence
- ✅ History table
- ✅ Responsive UI
- ✅ Error handling
- ✅ CORS enabled

## 🔧 Troubleshooting
- **Backend won't start**: Check MySQL running, update DB password
- **Frontend can't connect**: Backend must run on port 8080
- **CORS error**: Already enabled with `@CrossOrigin(origins="*")`
- **Maven errors**: Run `mvn clean install` first

## 📱 Screenshot Preview
Modern centered UI with gradient backgrounds, responsive design, clean tables.

**Project Complete & Production-Ready! 🚀**

