package com.lumen.minuAPI.Upbit;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UpbitController {
    @GetMapping("/upbit")
    public ResponseEntity<?> upbit(
        @RequestParam(defaultValue = "minutes/15") String term,
        @RequestParam(defaultValue = "KRW") String transfrom,
        @RequestParam(defaultValue = "BTC") String transto,
        @RequestParam(defaultValue = "10") int count
    ) {
        String baseUrl = "https://api.upbit.com/v1/candles";

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("/").append(term);
        urlBuilder.append("?market=").append(transfrom + "-" + transto);
        urlBuilder.append("&count=").append(count);

        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(urlBuilder.toString(), String.class);
            result = "{\"result\":" + result + "}";

            @SuppressWarnings("unchecked")
            Map<String, Object> jsonResponse = new ObjectMapper().readValue(result, Map.class);

            return ResponseEntity.ok(jsonResponse);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(500)
                .body(
                    new JSONObject()
                        .put("error", "Failed to fetch data from Upbit API")
                        .put("message", e.getMessage())
                );
        }
    }
}
