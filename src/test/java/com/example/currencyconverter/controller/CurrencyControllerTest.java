package com.example.currencyconverter.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.currencyconverter.model.CurrencyRequest;
import com.example.currencyconverter.model.CurrencyResponse;
import com.example.currencyconverter.service.CurrencyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

class CurrencyControllerTest {
    private CurrencyController currencyController;
    private CurrencyService mockCurrencyService;

    @BeforeEach
    void setUp() {
        mockCurrencyService = mock(CurrencyService.class);
        currencyController = new CurrencyController(mockCurrencyService);
    }

    @Test
    void testGetExchangeRates() {
        when(mockCurrencyService.getExchangeRates("USD")).thenReturn(Map.of("INR", 85.835084));

        ResponseEntity<?> responseEntity = currencyController.getExchangeRates("USD");

        assertEquals(200, responseEntity.getStatusCodeValue());
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertEquals("USD", responseBody.get("base"));
        assertEquals(85.835084, ((Map<String, Double>) responseBody.get("rates")).get("INR"));

        verify(mockCurrencyService, times(1)).getExchangeRates("USD");
    }

    @Test
    void testConvertCurrency() {
        CurrencyRequest request = new CurrencyRequest();
        request.setFrom("USD");  
        request.setTo("INR");    
        request.setAmount(100.0);

        when(mockCurrencyService.getExchangeRate("USD", "INR")).thenReturn(85.835084);
        when(mockCurrencyService.convertCurrency(100.0, 85.835084)).thenReturn(8583.5084);

        ResponseEntity<?> responseEntity = currencyController.convertCurrency(request);

        
        CurrencyResponse response = (CurrencyResponse) responseEntity.getBody();

        assertNotNull(response);
        assertEquals("USD", response.getFrom()); 
        assertEquals("INR", response.getTo());   
        assertEquals(100.0, response.getAmount(), 0.01);
        assertEquals(8583.5084, response.getConvertedAmount(), 0.01);

        verify(mockCurrencyService, times(1)).getExchangeRate("USD", "INR");
        verify(mockCurrencyService, times(1)).convertCurrency(100.0, 85.835084);
    }
}
