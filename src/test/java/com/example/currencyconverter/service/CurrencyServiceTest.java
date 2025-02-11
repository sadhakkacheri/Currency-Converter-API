package com.example.currencyconverter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.currencyconverter.utils.ExternalApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class CurrencyServiceTest {
    private CurrencyService currencyService;
    private ExternalApiClient mockApiClient;

    @BeforeEach
    void setUp() {
        mockApiClient = Mockito.mock(ExternalApiClient.class);
        currencyService = new CurrencyService(mockApiClient);
    }

    @Test
    void testGetExchangeRates() {
        when(mockApiClient.fetchExchangeRates("USD")).thenReturn(Map.of("INR", 85.835084));

        Map<String, Double> rates = currencyService.getExchangeRates("USD");

        assertEquals(85.835084, rates.get("INR"), 0.01);
        verify(mockApiClient, times(1)).fetchExchangeRates("USD");
    }

    @Test
    void testGetExchangeRateValidCurrencies() {
        when(mockApiClient.fetchExchangeRate("USD", "INR")).thenReturn(85.835084);

        double rate = currencyService.getExchangeRate("USD", "INR");
        assertEquals(85.835084, rate, 0.01);
        verify(mockApiClient, times(1)).fetchExchangeRate("USD", "INR");
    }

    @Test
    void testGetExchangeRateInvalidCurrency() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyService.getExchangeRate("INVALID", "INR");
        });

        assertTrue(exception.getMessage().contains("invalid currency code!"));
    }

    @Test
    void testConvertCurrency() {
        double result = currencyService.convertCurrency(100, 85.835084);
        assertEquals(8583.5084, result, 0.01);
    }

    @Test
    void testConvertCurrencyNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyService.convertCurrency(-100, 85.835084);
        });

        assertTrue(exception.getMessage().contains("enter a valid amount!"));
    }
}
