# Task1-NumberGame - FIXED

Number Guessing Game - Full Stack (Spring Boot + HTML/JS + H2 In-Memory DB)

✅ **"Failed to start game" error FIXED** - No MySQL required!

## 🚀 Exact Steps to Run

### 1. Open Terminal in VSCode (backend folder)
```
cd "FullStack-Projects/Task1-NumberGame/backend"
```

### 2. Clean and Compile
```
mvn clean compile
```

### 3. Start Backend Server
```
mvn spring-boot:run
```
Wait for "Started NumberGameApplication" message. Server runs on **http://localhost:8080**

### 4. Open Game in Browser
```
http://localhost:8080/
```
Game auto-starts. No manual MySQL setup needed (uses H2 in-memory DB).

## 🛠️ Features
- Guess number 1-100 (5 attempts)
- Leaderboard with top scores
- Session-based gameplay
- Full error handling + debug logs

## 🔍 Debug
- Frontend: Open Browser Console (F12)
- Backend: Check terminal logs (DEBUG level)
- H2 Console: http://localhost:8080/h2-console (JDBC: jdbc:h2:mem:game_db)

## API Endpoints
- POST /api/game/start
- GET /api/game/guess?guessNum=50  
- POST /api/game/reset
- GET /api/game/scores

