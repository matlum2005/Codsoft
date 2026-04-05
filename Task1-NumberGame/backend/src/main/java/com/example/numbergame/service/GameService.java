package com.example.numbergame.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.numbergame.model.GameScore;
import com.example.numbergame.repository.GameScoreRepository;

@Service
public class GameService {

    @Autowired
    private GameScoreRepository gameScoreRepository;

    public void saveScore(int attemptsUsed, int score) {
        GameScore gameScore = new GameScore(attemptsUsed, score);
        gameScoreRepository.save(gameScore);
    }

    public List<GameScore> getTopScores() {
        return gameScoreRepository.findTop10ByOrderByScoreDesc();
    }
}

