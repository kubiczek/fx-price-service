package com.bigbank.fxpriceservice.service;

import com.bigbank.fxpriceservice.domain.Price;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
class ClientPriceCalculator {

    public Price calculate(Price price) {
        return Price.builder()
                .bid(price.getBid().multiply(BigDecimal.valueOf(0.999)).setScale(price.getBid().scale(), RoundingMode.HALF_UP))
                .ask(price.getAsk().multiply(BigDecimal.valueOf(1.001)).setScale(price.getAsk().scale(), RoundingMode.HALF_UP))
                .build();
    }
}
