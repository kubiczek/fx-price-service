package com.bigbank.fxpriceservice.web;

import com.bigbank.fxpriceservice.domain.Price;
import com.bigbank.fxpriceservice.service.ClientPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ClientPriceController {

    @Autowired
    private ClientPriceService service;

    @GetMapping("/latest-price/{baseCurrency}/{quoteCurrency}")
    public Price getLatestPrice(
            @PathVariable("baseCurrency") String baseCurrency,
            @PathVariable("quoteCurrency") String quoteCurrency
    ) {
        final var ccyPair = baseCurrency + "/" + quoteCurrency;
        return service.getLatestPrice(ccyPair)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency pair not found: " + ccyPair));
    }
}
