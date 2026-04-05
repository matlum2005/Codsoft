package com.example.atm.controller;

import com.example.atm.service.AtmService;
import com.example.atm.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AtmController {

    @Autowired
    private AtmService atmService;

    public static class LoginRequest {
        public String accountNumber;
        public String pin;
    }

    public static class TransactionRequest {
        public String accountNumber;
        public double amount;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        String result = atmService.login(request.accountNumber, request.pin);
        Map<String, Object> response = new HashMap<>();
        response.put("success", result.equals("Login successful"));
        response.put("message", result);
        if (result.equals("Login successful")) {
            response.put("accountNumber", request.accountNumber);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/atm/deposit")
    public ResponseEntity<Map<String, Object>> deposit(@RequestBody TransactionRequest request) {
        String result = atmService.deposit(request.accountNumber, request.amount);
        Map<String, Object> response = new HashMap<>();
        boolean success = !result.startsWith("Error:");
        response.put("status", success ? "success" : "error");
        response.put("message", result);
        if (success) {
            response.put("balance", atmService.getBalance(request.accountNumber));
        }
        return ResponseEntity.ok(response);
    }

@PostMapping("/atm/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(@RequestBody TransactionRequest request) {
        String result = atmService.withdraw(request.accountNumber, request.amount);
        Map<String, Object> response = new HashMap<>();
        boolean success = !result.startsWith("Error:");
        response.put("status", success ? "success" : "error");
        response.put("message", result);
        if (success) {
            response.put("balance", atmService.getBalance(request.accountNumber));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/atm/lastTransaction/{accountNumber}")
    public ResponseEntity<Transaction> getLastTransaction(@PathVariable String accountNumber) {
        Transaction tx = atmService.getLastTransaction(accountNumber);
        if (tx == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(tx);
    }

    @GetMapping("/atm/balance/{accountNumber}")
    public ResponseEntity<Double> getBalance(@PathVariable String accountNumber) {
        double balance = atmService.getBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }
}
