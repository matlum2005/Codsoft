package com.example.atm.service;

import com.example.atm.model.Account;
import com.example.atm.model.Transaction;
import com.example.atm.repository.AccountRepository;
import com.example.atm.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AtmService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // 🔐 LOGIN
    public String login(String accountNumber, String pin) {
        if (accountNumber == null || accountNumber.trim().isEmpty() ||
            pin == null || pin.trim().isEmpty()) {
            return "Error: Account number and PIN required";
        }

        Optional<Account> optionalAccount =
                accountRepository.findByAccountNumberAndPin(accountNumber.trim(), pin.trim());

        if (optionalAccount.isEmpty()) {
            return "Invalid account number or PIN";
        }

        return "Login successful";
    }

    
    public String deposit(String accountNumber, double amount) {

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return "Error: Account number required";
        }

        if (amount <= 0) {
            return "Error: Amount must be greater than 0";
        }

        Optional<Account> optionalAccount =
                accountRepository.findByAccountNumber(accountNumber.trim());

        if (optionalAccount.isEmpty()) {
            return "Error: Account not found"; // ❌ auto-create hata diya
        }

        Account account = optionalAccount.get();

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

       
        Transaction tx = new Transaction();
        tx.setAccountNumber(accountNumber);
        tx.setType("DEPOSIT");
        tx.setAmount(amount);
        tx.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tx);

        return "Deposit successful. New balance: " + account.getBalance();
    }

   
    public String withdraw(String accountNumber, double amount) {

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return "Error: Account number required";
        }

        if (amount <= 0) {
            return "Error: Amount must be greater than 0";
        }

        Optional<Account> optionalAccount =
                accountRepository.findByAccountNumber(accountNumber.trim());

        if (optionalAccount.isEmpty()) {
            return "Error: Account not found";
        }

        Account account = optionalAccount.get();

        if (account.getBalance() < amount) {
            return "Error: Insufficient balance";
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        
        Transaction tx = new Transaction();
        tx.setAccountNumber(accountNumber);
        tx.setType("WITHDRAW");
        tx.setAmount(amount);
        tx.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tx);

        return "Withdraw successful. New balance: " + account.getBalance();
    }

    
    public Transaction getLastTransaction(String accountNumber) {
        return transactionRepository
                .findFirstByAccountNumberOrderByTimestampDesc(accountNumber)
                .orElse(null);
    }

    
    public double getBalance(String accountNumber) {
        Optional<Account> optionalAccount =
                accountRepository.findByAccountNumber(accountNumber.trim());

        if (optionalAccount.isEmpty()) {
            return 0.0;
        }

        return optionalAccount.get().getBalance();
    }
}