# Currency Converter - Full Stack Web Application

Complete Full Stack Currency Converter using **Spring Boot + MySQL + Vanilla JS**.

## 📋 Features
- ✅ Live exchange rates from exchangerate-api.com
- ✅ Real-time currency conversion
- ✅ Modern responsive UI
- ✅ Conversion history saved in MySQL
- ✅ Input validation (frontend + backend)
- ✅ Currency swap button
- ✅ Loading states & error handling
- ✅ REST APIs with CORS

## 🛠 Tech Stack
- **Backend**: Spring Boot 3.2.0, JPA, MySQL
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Database**: MySQL 8.0+
- **External API**: exchangerate-api.com (free)

## 🚀 Quick Start

### 1. Prerequisites
```
- Java 17+
- Maven 3.6+
- MySQL 8.0+ (root/password or update application.properties)
- VS Code
```

### 2. Setup MySQL Database
```bash
# Run in MySQL Workbench / CLI
mysql -u root -p < create_database.sql
```

**Default credentials** (update in `src/main/resources/application.properties`):
```
username: root
password: password
database: currency_db
```

### 3. Run Application
```bash
cd "c:/Users/DELL/Desktop/Codsoft task 4"
mvn clean install
mvn spring-boot:run
```

### 4. Access Application
```
Frontend: http://localhost:8080
APIs:
  - GET /api/rates?base=USD
  - POST /api/convert
```

## 🧪 Test APIs (Postman/Curl)

**Get Rates:**
```bash
curl "http://localhost:8080/api/rates?base=USD"
```

**Convert:**
```bash
curl -X POST http://localhost:8080/api/convert \
  -H "Content-Type: application/json" \
  -d '{"baseCurrency":"USD","targetCurrency":"EUR","amount":100}'
```

## 📁 Project Structure
```
├── pom.xml                    # Maven dependencies
├── src/main/java/...          # Spring Boot backend
│   ├── CurrencyConverterApplication.java
│   ├── controller/
│   ├── service/
│   ├── entity/
│   ├── dto/
│   └── repository/
├── src/main/resources/        # Config + Frontend
│   ├── application.properties
│   └── static/                # HTML/CSS/JS
│       ├── index.html
│       ├── css/style.css
│       └── js/script.js
├── create_database.sql        # MySQL setup
└── README.md
```

## 🎨 Frontend Features
- Modern gradient design
- Responsive (mobile-first)
- Swap currencies ↔
- Loading spinner
- Success/error states
- Auto-clear after conversion

## 🔧 Backend Features
- REST APIs (/api/rates, /api/convert)
- JPA + MySQL persistence
- External API integration
- Input validation (@Valid)
- CORS enabled
- Exception handling
- Transactional saves

## 🐛 Troubleshooting

**Maven errors:**
```bash
mvn clean install -U
```

**MySQL connection:**
```
- Start MySQL service
- Update credentials in application.properties
- Run create_database.sql
```

**Port 8080 busy:**
```
server.port=8081  # in application.properties
```

**Lombok IDE errors:**
```
- Install Lombok plugin in VS Code/IntelliJ
- Or ignore - `mvn compile` works fine
```

## 📊 Database Schema
```
conversion_history:
- id (PK)
- base_currency
- target_currency  
- amount
- converted_amount
- rate
- timestamp
```

## 📈 Production Deployment
```
# Docker + MySQL ready
# Add spring.profiles.active=prod
# Environment variables for DB credentials
```

**Enjoy your Currency Converter! 🚀**

