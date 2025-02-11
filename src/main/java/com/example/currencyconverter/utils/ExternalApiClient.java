package com.example.currencyconverter.utils;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class ExternalApiClient {

    public Map<String, Double> fetchExchangeRates(String baseCurrency) {
        
       
        return Map.of("EUR", 0.94, "INR", 85.83, "GBP", 0.82);
    }

    public double fetchExchangeRate(String fromCurrency, String toCurrency) {
        
        if (fromCurrency.equals("USD") && toCurrency.equals("INR")) {
            return 85.83;
        }
        return 0.94; 
    }
}
