package com.example.numbergame.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.numbergame.model.GameScore;
import com.example.numbergame.service.GameService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/game")
public class GameController {
    
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public Map<String, Object> start(HttpSession session) {
        logger.info("Starting new game for session: {}", session.getId());
        int randomNumber = (int) (Math.random() * 100) + 1;
        session.setAttribute("randomNumber", randomNumber);
        session.setAttribute("attemptsLeft", 5);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "New game started! Guess a number between 1-100.");
        response.put("attemptsLeft", 5);
        response.put("status", "ongoing");
        logger.debug("Game started with randomNumber: {}, attemptsLeft: {}", randomNumber, 5);
        return response;
    }

    @GetMapping("/guess")
    public Map<String, Object> guess(@RequestParam int guessNum, HttpSession session) {
        logger.info("Guess attempt: {} for session: {}", guessNum, session.getId());
        Integer randomNumberObj = (Integer) session.getAttribute("randomNumber");
        Integer attemptsLeftObj = (Integer) session.getAttribute("attemptsLeft");

        Map<String, Object> response = new HashMap<>();

        if (randomNumberObj == null || attemptsLeftObj == null) {
            response.put("message", "Please start a new game first!");
            response.put("status", "notStarted");
            return response;
        }

        int randomNumber = randomNumberObj;
        int attemptsLeft = attemptsLeftObj;

        if (attemptsLeft <= 0) {
            response.put("message", "No attempts left! Play again.");
            response.put("attemptsLeft", 0);
            response.put("status", "lost");
            return response;
        }

        String message;
        if (guessNum == randomNumber) {
            int attemptsUsed = 6 - attemptsLeft; // +1 for current
            int score = Math.max(50, 100 - (attemptsUsed * 10));
            gameService.saveScore(attemptsUsed, score);
            message = String.format("Correct! 🎉 Score: %d", score);
            response.put("status", "won");
        } else if (guessNum < randomNumber) {
            message = "Too low! 📈 Try higher.";
        } else {
            message = "Too high! 📉 Try lower.";
        }

        attemptsLeft--;
        session.setAttribute("attemptsLeft", attemptsLeft);

        response.put("message", message);
        response.put("attemptsLeft", attemptsLeft);
        response.put("status", attemptsLeft > 0 ? "ongoing" : "lost");

        return response;
    }

    @PostMapping("/reset")
    public Map<String, Object> reset(HttpSession session) {
        session.invalidate();
        return start(session);
    }

    @GetMapping("/scores")
    public List<GameScore> getScores() {
        return gameService.getTopScores();
    }
}

