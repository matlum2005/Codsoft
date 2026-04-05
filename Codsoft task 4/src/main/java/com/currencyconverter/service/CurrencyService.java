package com.currencyconverter.service;

import com.currencyconverter.dto.ConversionRequest;
import com.currencyconverter.dto.ConversionResponse;
import com.currencyconverter.dto.ExchangeRatesResponse;
import com.currencyconverter.entity.ConversionHistory;
import com.currencyconverter.repository.ConversionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Service
public class CurrencyService {

    private final ConversionRepository conversionRepository;
    private final ObjectMapper objectMapper;

    public CurrencyService(ConversionRepository conversionRepository, ObjectMapper objectMapper) {
        this.conversionRepository = conversionRepository;
        this.objectMapper = objectMapper;
    }

    @Value("${currency.api.base-url}")
    private String apiBaseUrl;

    public ExchangeRatesResponse getExchangeRates(String baseCurrency) {
        try {
            String url = apiBaseUrl + baseCurrency;
            System.out.println("🌐 Fetching rates from: " + url);
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("📡 External API status: " + response.statusCode());
            System.out.println("📄 Response preview: " + response.body().substring(0, Math.min(200, response.body().length())) + "...");
            
            if (response.statusCode() == 200) {
                ExchangeRatesResponse rates = objectMapper.readValue(response.body(), ExchangeRatesResponse.class);
                System.out.println("✅ Parsed " + rates.getRates().size() + " currency rates for base: " + rates.getBase());
                return rates;
            } else {
                System.err.println("⚠️ External API failed, using fallback rates for " + baseCurrency);
                return createFallbackRates(baseCurrency);
            }
        } catch (Exception e) {
            System.err.println("❌ External API Error for " + baseCurrency + ": " + e.getMessage());
            System.err.println("⚠️ Using fallback rates");
            return createFallbackRates(baseCurrency);
        }
    }

    private ExchangeRatesResponse createFallbackRates(String baseCurrency) {
        Map<String, Double> fallbackRates = FullCurrencyList.getFullRates(baseCurrency);
        
        ExchangeRatesResponse fallback = new ExchangeRatesResponse();
        fallback.setBase(baseCurrency);
        fallback.setRates(fallbackRates);
        return fallback;
    }

    @Transactional
    public ConversionResponse convertCurrency(ConversionRequest request) {
        if (request.getBaseCurrency().equals(request.getTargetCurrency())) {
            BigDecimal converted = BigDecimal.valueOf(request.getAmount()).setScale(2, RoundingMode.HALF_UP);
            return new ConversionResponse(
                request.getBaseCurrency(),
                request.getTargetCurrency(),
                request.getAmount(),
                converted,
                BigDecimal.ONE
            );
        }

        try {
            ExchangeRatesResponse rates = getExchangeRates(request.getBaseCurrency());
            Map<String, Double> rateMap = rates.getRates();

            if (!rateMap.containsKey(request.getTargetCurrency())) {
                throw new IllegalArgumentException("Target currency '" + request.getTargetCurrency() + "' not supported for base '" + request.getBaseCurrency() + "'");
            }

            Double rateValue = rateMap.get(request.getTargetCurrency());
            BigDecimal rate = BigDecimal.valueOf(rateValue).setScale(4, RoundingMode.HALF_UP);
            BigDecimal amountBd = BigDecimal.valueOf(request.getAmount());
            BigDecimal convertedAmount = amountBd.multiply(rate).setScale(2, RoundingMode.HALF_UP);

            // Save to history
            ConversionHistory history = new ConversionHistory();
            history.setBaseCurrency(request.getBaseCurrency());
            history.setTargetCurrency(request.getTargetCurrency());
            history.setAmount(amountBd);
            history.setConvertedAmount(convertedAmount);
            history.setRate(rate);
            history.setTimestamp(LocalDateTime.now());
            conversionRepository.save(history);

            return new ConversionResponse(
                request.getBaseCurrency(),
                request.getTargetCurrency(),
                request.getAmount(),
                convertedAmount,
                rate
            );
        } catch (IllegalArgumentException e) {
            throw e; // Re-throw currency errors
        } catch (Exception e) {
            throw new IllegalArgumentException("Exchange rate service unavailable: " + e.getMessage());
        }
    }
}
