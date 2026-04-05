package com.example.numbergame.repository;

import com.example.numbergame.model.GameScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {
    List<GameScore> findTop10ByOrderByScoreDesc();
}

