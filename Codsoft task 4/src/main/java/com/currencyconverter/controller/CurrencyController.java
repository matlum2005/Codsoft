package com.currencyconverter.controller;

import com.currencyconverter.dto.ConversionRequest;
import com.currencyconverter.dto.ConversionResponse;
import com.currencyconverter.dto.ExchangeRatesResponse;
import com.currencyconverter.service.CurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/rates")
    public ResponseEntity<ExchangeRatesResponse> getRates(@RequestParam String base) {
        try {
            ExchangeRatesResponse rates = currencyService.getExchangeRates(base.toUpperCase());
            return ResponseEntity.ok(rates);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/convert")
    public ResponseEntity<?> convert(@Valid @RequestBody ConversionRequest request) {
        long startTime = System.currentTimeMillis();
        System.out.println("📥 /api/convert POST received: Base='" + request.getBaseCurrency() + "', Target='" + request.getTargetCurrency() + "', Amount=" + request.getAmount());
        
        try {
            ConversionResponse response = currencyService.convertCurrency(request);
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("✅ /api/convert SUCCESS (" + duration + "ms)");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            System.err.println("❌ /api/convert FAILED (" + duration + "ms): " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Conversion failed: " + e.getMessage()));
        }
    }
}
