package com.bigbank.fxpriceservice.messaging;

import com.bigbank.fxpriceservice.domain.MarketPriceFeed;
import com.bigbank.fxpriceservice.domain.Price;
import com.bigbank.fxpriceservice.service.ClientPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class MarketPriceListener {

    @Autowired
    private ClientPriceService service;

    public void onMessage(String message) {
        final var feed = fromCsv(message);
        service.addPrice(feed.getCcyPair(), feed.getPrice());
    }

    private static MarketPriceFeed fromCsv(String csvLine) {
        final var csvFields = csvLine.split(",");
        return MarketPriceFeed.builder()
                .id(csvFields[0])
                .ccyPair(csvFields[1])
                .price(Price.builder()
                        .bid(toBigDecimal(csvFields[2]))
                        .ask(toBigDecimal(csvFields[3]))
                        .build())
                .timestamp(csvFields[4])
                .build();
    }

    private static BigDecimal toBigDecimal(String price) {
        final var bidScale = price.length() - price.indexOf(".") - 1;
        return new BigDecimal(price).setScale(bidScale, RoundingMode.HALF_UP);
    }
}
