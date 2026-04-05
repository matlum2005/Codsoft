package com.currencyconverter.dto;

import java.util.Map;
import java.util.Objects;

public class ExchangeRatesResponse {
    private String base;
    private Map<String, Double> rates;
    
    // Constructors
    public ExchangeRatesResponse() {}
    
    public ExchangeRatesResponse(String base, Map<String, Double> rates) {
        this.base = base;
        this.rates = rates;
    }
    
    // Getters and Setters
    public String getBase() {
        return base;
    }
    
    public void setBase(String base) {
        this.base = base;
    }
    
    public Map<String, Double> getRates() {
        return rates;
    }
    
    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRatesResponse that = (ExchangeRatesResponse) o;
        return Objects.equals(base, that.base) &&
               Objects.equals(rates, that.rates);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(base, rates);
    }
}

