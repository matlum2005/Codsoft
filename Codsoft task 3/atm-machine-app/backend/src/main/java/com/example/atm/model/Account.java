package com.example.atm.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

@Column(nullable = false)
    private String accountHolderName;

    @Column(nullable = false)
    private String pin;

    @Column(nullable = false)
    private double balance = 0.0;
}
