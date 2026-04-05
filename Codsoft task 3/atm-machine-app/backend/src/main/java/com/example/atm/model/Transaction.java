package com.example.atm.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String type; // DEPOSIT or WITHDRAW

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}

