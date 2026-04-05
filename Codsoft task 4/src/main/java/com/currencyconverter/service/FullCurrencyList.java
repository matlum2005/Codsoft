package com.currencyconverter.service;

import java.util.Map;
import java.util.HashMap;

public class FullCurrencyList {
    public static Map<String, Double> getFullRates(String baseCurrency) {
        // Full ISO 4217 currencies (~170) with approximate USD rates (2024)
        Map<String, Double> rates = new HashMap<>();
        
        // Major currencies
        rates.put("AED", 3.67); rates.put("AFN", 70.50); rates.put("ALL", 91.20); rates.put("AMD", 387.00);
        rates.put("ANG", 1.79); rates.put("AOA", 850.00); rates.put("ARS", 950.00); rates.put("AUD", 1.51);
        rates.put("AWG", 1.79); rates.put("AZN", 1.70); rates.put("BAM", 1.80); rates.put("BBD", 2.00);
        rates.put("BDT", 117.50); rates.put("BGN", 1.80); rates.put("BHD", 0.38); rates.put("BIF", 2880.00);
        rates.put("BMD", 1.00); rates.put("BND", 1.34); rates.put("BOB", 6.87); rates.put("BRL", 5.60);
        rates.put("BSD", 1.00); rates.put("BTN", 83.80); rates.put("BWP", 13.50); rates.put("BYN", 3.25);
        rates.put("BZD", 2.01); rates.put("CAD", 1.38); rates.put("CDF", 2850.00); rates.put("CHF", 0.87);
        rates.put("CLP", 940.00); rates.put("CNY", 7.25); rates.put("COP", 4100.00); rates.put("CRC", 510.00);
        rates.put("CUC", 1.00); rates.put("CUP", 24.00); rates.put("CVE", 101.00); rates.put("CZK", 23.00);
        rates.put("DJF", 178.00); rates.put("DKK", 6.90); rates.put("DOP", 59.00); rates.put("DZD", 134.00);
        
        // Continue with comprehensive list
        rates.put("EGP", 48.50); rates.put("ERN", 15.00); rates.put("ETB", 57.00); rates.put("EUR", 0.92);
        rates.put("FJD", 2.25); rates.put("FKP", 0.79); rates.put("GBP", 0.79); rates.put("GEL", 2.70);
        rates.put("GGP", 0.79); rates.put("GHS", 15.50); rates.put("GIP", 0.79); rates.put("GMD", 67.50);
        rates.put("GNF", 8600.00); rates.put("GTQ", 7.80); rates.put("GYD", 209.00); rates.put("HKD", 7.80);
        rates.put("HNL", 24.70); rates.put("HRK", 7.45); rates.put("HTG", 132.00); rates.put("HUF", 360.00);
        rates.put("IDR", 15900.00); rates.put("ILS", 3.70); rates.put("IMP", 0.79); rates.put("INR", 83.80);
        rates.put("IQD", 1310.00); rates.put("IRR", 42000.00); rates.put("ISK", 138.00); rates.put("JEP", 0.79);
        rates.put("JMD", 156.00); rates.put("JOD", 0.71); rates.put("JPY", 149.50); rates.put("KES", 129.00);
        rates.put("KGS", 89.00); rates.put("KHR", 4100.00); rates.put("KMF", 450.00); rates.put("KPW", 900.00);
        rates.put("KRW", 1340.00); rates.put("KWD", 0.31); rates.put("KYD", 0.83); rates.put("KZT", 480.00);
        
        // More currencies
        rates.put("LAK", 22000.00); rates.put("LBP", 15000.00); rates.put("LKR", 300.00); rates.put("LRD", 195.00);
        rates.put("LSL", 18.50); rates.put("LYD", 4.80); rates.put("MAD", 10.00); rates.put("MDL", 17.70);
        rates.put("MGA", 4500.00); rates.put("MKD", 56.50); rates.put("MMK", 2100.00); rates.put("MNT", 3450.00);
        rates.put("MOP", 8.05); rates.put("MRU", 39.50); rates.put("MUR", 46.50); rates.put("MVR", 15.40);
        rates.put("MWK", 1730.00); rates.put("MXN", 19.80); rates.put("MYR", 4.70); rates.put("MZN", 63.50);
        rates.put("NAD", 18.50); rates.put("NGN", 1600.00); rates.put("NIO", 36.50); rates.put("NOK", 10.80);
        rates.put("NPR", 133.00); rates.put("NZD", 1.67); rates.put("OMR", 0.38); rates.put("PAB", 1.00);
        rates.put("PEN", 3.75); rates.put("PGK", 3.90); rates.put("PHP", 58.00); rates.put("PKR", 278.00);
        rates.put("PLN", 4.00); rates.put("PYG", 7500.00); rates.put("QAR", 3.64); rates.put("RON", 4.60);
        rates.put("RSD", 108.00); rates.put("RUB", 97.00); rates.put("RWF", 1320.00); rates.put("SAR", 3.75);
        
        // Additional currencies including STN and others
        rates.put("SBD", 8.40); rates.put("SCR", 13.50); rates.put("SDG", 620.00); rates.put("SEK", 10.60);
        rates.put("SGD", 1.34); rates.put("SHP", 0.79); rates.put("SLE", 21.80); rates.put("SLL", 22700.00);
        rates.put("SOS", 570.00); rates.put("SRD", 34.00); rates.put("SSP", 1300.00); rates.put("STN", 22.50);
        rates.put("SYP", 13000.00); rates.put("SZL", 18.50); rates.put("THB", 36.50); rates.put("TJS", 10.70);
        rates.put("TMT", 3.50); rates.put("TND", 3.10); rates.put("TOP", 2.35); rates.put("TRY", 34.00);
        rates.put("TTD", 6.75); rates.put("TWD", 32.30); rates.put("TZS", 2700.00); rates.put("UAH", 41.00);
        rates.put("UGX", 3700.00); rates.put("USD", 1.00); rates.put("UYU", 40.50); rates.put("UZS", 12600.00);
        rates.put("VES", 36.50); rates.put("VND", 25300.00); rates.put("VUV", 119.00); rates.put("WST", 2.72);
        rates.put("XAF", 600.00); rates.put("XCD", 2.70); rates.put("XDR", 0.75); rates.put("XOF", 600.00);
        rates.put("XPF", 111.00); rates.put("YER", 250.00); rates.put("ZAR", 18.50); rates.put("ZMW", 27.00);
        rates.put("ZWL", 322.00);
        
        // Base currency always 1.0
        rates.put(baseCurrency.toUpperCase(), 1.0);
        
        System.out.println("✅ Full fallback: " + rates.size() + " currencies for base " + baseCurrency);
        return rates;
    }
}

