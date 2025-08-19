package com.lumen.minuAPI.OpenAPI.Binance;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BinanceService {
    
    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public BinanceDTO fetchBinanceData(
        String apiKey, String secretKey,
        String symbol, String interval, int limit
    ) {
        String baseUrl = "https://api.binance.com/api/v3/klines";
        String url = buildUrl(baseUrl, symbol, interval, limit);
        
        try {
            String result = restTemplate.getForObject(url, String.class);
            result = BinanceUtil.jsontransform(result);
            Map<String, Object> resultMap = new ObjectMapper().readValue(result, Map.class);
            return new BinanceDTO(resultMap); // Placeholder for actual data mapping
        } catch (Exception e) {
            e.printStackTrace();
            return new BinanceDTO(
                Map.of("error", "Failed to fetch data from Binance API", "message", e.getMessage()));
        }
    }

    private String buildUrl(String baseUrl, String symbol, String interval, int limit) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl)
                .append("?symbol=").append(symbol)
                .append("&interval=").append(interval)
                .append("&limit=").append(limit);

        return urlBuilder.toString();
    }
}
