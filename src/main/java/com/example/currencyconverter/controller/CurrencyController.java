package com.example.currencyconverter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.currencyconverter.model.CurrencyRequest;
import com.example.currencyconverter.model.CurrencyResponse;
import com.example.currencyconverter.service.CurrencyService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    
    @GetMapping("/rates")
    public ResponseEntity<?> getExchangeRates(@RequestParam(defaultValue = "USD") String base) {
        try {
            base = sanitizeCurrencyCode(base);
            Map<String, Double> rates = currencyService.getExchangeRates(base);
            Map<String, Object> response = new HashMap<>();
            response.put("base", base);
            response.put("rates", rates);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid base currency or API unavailable"));
        }
    }

    @PostMapping("/convert")
    public ResponseEntity<?> convertCurrency(@RequestBody CurrencyRequest request) {
        try {
            String fromCurrency = sanitizeCurrencyCode(request.getFrom());
            String toCurrency = sanitizeCurrencyCode(request.getTo());

            double exchangeRate = currencyService.getExchangeRate(fromCurrency, toCurrency);
            double convertedAmount = currencyService.convertCurrency(request.getAmount(), exchangeRate);

            return ResponseEntity.ok(new CurrencyResponse(
                    fromCurrency, toCurrency, request.getAmount(), convertedAmount
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Internal Server Error"));
        }
    }

    private String sanitizeCurrencyCode(String currencyCode) {
        if (currencyCode == null || currencyCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency code cannot be null or empty!");
        }
        return currencyCode.trim().toUpperCase();
    }
}
