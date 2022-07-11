package com.bigbank.fxpriceservice.service;

import com.bigbank.fxpriceservice.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientPriceService {

    @Autowired
    private ClientPriceCalculator calculator;

    private Map<String, Price> latestPrices = new HashMap<>();

    public Optional<Price> getLatestPrice(String ccyPair) {
        return Optional.ofNullable(latestPrices.get(ccyPair.toUpperCase()));
    }

    public void addPrice(String ccyPair, Price price) {
        latestPrices.put(ccyPair.toUpperCase(), calculator.calculate(price));
    }

}
