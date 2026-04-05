# ATM Machine Simulation System

Internship-level Full Stack project with Spring Boot + MySQL + Vanilla JS.

## Tech Stack
- Backend: Java Spring Boot 3.2, JPA, MySQL
- Frontend: HTML/CSS/JS
- Sample Account: `12345` / PIN `1234` (balance $5000)

## Setup MySQL
```sql
CREATE DATABASE atm_db;
-- Use existing root/8595629739 or create user
```

## Run Backend
```bash
cd atm-machine-app/backend
mvn spring-boot:run
```
Server: http://localhost:8080

## Run Frontend
Open `atm-machine-app/frontend/index.html` in browser (or Live Server).

## Test Flow
1. Login: 12345 / 1234
2. Deposit $100 → Check balance & last tx
3. Withdraw $50 → Check updates
4. Logout/Login to verify persistence

## APIs
- POST /api/auth/login
- POST /api/atm/deposit
- POST /api/atm/withdraw  
- GET /api/atm/balance/{acc}
- GET /api/atm/lastTransaction/{acc}

**Fully functional!** 🚀

