package com.bigbank.fxpriceservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldResponseNotFound_whenCurrencyPairUnknown() throws Exception {
        mockMvc.perform(get("/latest-price/xxx/yyy"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldResponseOk_whenCurrencyPairFound() throws Exception {
        publish("106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001");

        mockMvc.perform(get("/latest-price/eur/usd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bid").value("1.0989"))
                .andExpect(jsonPath("$.ask").value("1.2012"));


        publish("107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002");

        mockMvc.perform(get("/latest-price/eur/jpy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bid").value("119.48"))
                .andExpect(jsonPath("$.ask").value("120.02"));

        publish("108,GBP/USD,1.2500,1.2560,01-06-2020 12:01:02:002");

        mockMvc.perform(get("/latest-price/gbp/usd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bid").value("1.2488"))
                .andExpect(jsonPath("$.ask").value("1.2573"));

        publish("109,GBP/USD,1.2499,1.2561,01-06-2020 12:01:02:100");

        mockMvc.perform(get("/latest-price/gbp/usd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bid").value("1.2487"))
                .andExpect(jsonPath("$.ask").value("1.2574"));

        publish("110,EUR/JPY,119.61,119.91,01-06-2020 12:01:02:110");

        mockMvc.perform(get("/latest-price/eur/jpy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bid").value("119.49"))
                .andExpect(jsonPath("$.ask").value("120.03"));
    }

    private void publish(String message) throws Exception {
        mockMvc.perform(post("/market-price").content(message))
                .andExpect(status().isOk());
    }
}