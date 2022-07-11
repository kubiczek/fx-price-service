package com.bigbank.fxpriceservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketPriceFeed {
    private String id;
    private String ccyPair;
    private Price price;
    private String timestamp;
}
