package com.example.numbergame.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_score")
public class GameScore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int attemptsUsed;
	
	@Column(nullable = false)
	private int score;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	// Constructors
	public GameScore() {
		this.createdAt = LocalDateTime.now();
	}
	
	public GameScore(int attemptsUsed, int score) {
		this.attemptsUsed = attemptsUsed;
		this.score = score;
		this.createdAt = LocalDateTime.now();
	}
	
	// Getters and Setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getAttemptsUsed() {
		return attemptsUsed;
	}
	
	public void setAttemptsUsed(int attemptsUsed) {
		this.attemptsUsed = attemptsUsed;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}

