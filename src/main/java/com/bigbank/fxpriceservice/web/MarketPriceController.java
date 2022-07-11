package com.bigbank.fxpriceservice.web;

import com.bigbank.fxpriceservice.messaging.MarketPriceListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketPriceController {
    @Autowired
    private MarketPriceListener listener;

    @PostMapping("/market-price")
    public void postMarketPrice(@RequestBody String body) {
        listener.onMessage(body);
    }
}
