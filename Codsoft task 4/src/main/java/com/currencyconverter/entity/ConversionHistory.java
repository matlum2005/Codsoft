package com.currencyconverter.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "conversion_history")
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_currency", nullable = false)
    private String baseCurrency;

    @Column(name = "target_currency", nullable = false)
    private String targetCurrency;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "converted_amount", nullable = false)
    private BigDecimal convertedAmount;

    @Column(nullable = false)
    private BigDecimal rate;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    // Constructors
    public ConversionHistory() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBaseCurrency() {
        return baseCurrency;
    }
    
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }
    
    public String getTargetCurrency() {
        return targetCurrency;
    }
    
    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }
    
    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
    
    public BigDecimal getRate() {
        return rate;
    }
    
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversionHistory that = (ConversionHistory) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(baseCurrency, that.baseCurrency) &&
               Objects.equals(targetCurrency, that.targetCurrency) &&
               Objects.equals(amount, that.amount) &&
               Objects.equals(convertedAmount, that.convertedAmount) &&
               Objects.equals(rate, that.rate) &&
               Objects.equals(timestamp, that.timestamp);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, baseCurrency, targetCurrency, amount, convertedAmount, rate, timestamp);
    }
}

