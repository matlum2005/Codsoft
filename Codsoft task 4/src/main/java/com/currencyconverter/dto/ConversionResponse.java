package com.currencyconverter.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ConversionResponse {
    private String base;
    private String target;
    private Double amount;
    private BigDecimal convertedAmount;
    private BigDecimal rate;
    
    // Constructors
    public ConversionResponse() {}
    
    public ConversionResponse(String base, String target, Double amount, BigDecimal convertedAmount, BigDecimal rate) {
        this.base = base;
        this.target = target;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.rate = rate;
    }
    
    // Getters and Setters
    public String getBase() {
        return base;
    }
    
    public void setBase(String base) {
        this.base = base;
    }
    
    public String getTarget() {
        return target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversionResponse that = (ConversionResponse) o;
        return Objects.equals(base, that.base) &&
               Objects.equals(target, that.target) &&
               Objects.equals(amount, that.amount) &&
               Objects.equals(convertedAmount, that.convertedAmount) &&
               Objects.equals(rate, that.rate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(base, target, amount, convertedAmount, rate);
    }
}

