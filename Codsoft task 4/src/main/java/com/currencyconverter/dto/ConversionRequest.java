package com.currencyconverter.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ConversionRequest {
    
    @NotBlank(message = "Base currency is required")
    private String baseCurrency;
    
    @NotBlank(message = "Target currency is required")
    private String targetCurrency;
    
    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be positive")
    private Double amount;
    
    // Constructors
    public ConversionRequest() {}
    
    public ConversionRequest(String baseCurrency, String targetCurrency, Double amount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
    }
    
    // Getters and Setters
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
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversionRequest that = (ConversionRequest) o;
        return Objects.equals(baseCurrency, that.baseCurrency) &&
               Objects.equals(targetCurrency, that.targetCurrency) &&
               Objects.equals(amount, that.amount);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(baseCurrency, targetCurrency, amount);
    }
}

