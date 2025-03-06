package com.lumen.minuAPI.OpenAPI.Upbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UpbitService {

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public UpbitDTO fetchUpbitData(String term, String transfrom, String transto, int count) {
        String url = buildUrl(term, transfrom, transto, count);

        try {
            String result = restTemplate.getForObject(url, String.class);
            result = "{\"result\":" + result + "}";

            Map<String, Object> jsonResponse = new ObjectMapper().readValue(result, Map.class);
            return new UpbitDTO(jsonResponse);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new UpbitDTO(
                (Map<String, Object>) new JSONObject()
                    .put("error", "Failed to fetch data from Upbit API")
                    .put("message", e.getMessage()));
        }
    }

    private String buildUrl(String term, String transfrom, String transto, int count) {
        return String.format(
            "https://api.upbit.com/v1/candles/%s?market=%s-%s&count=%d",
            term, transfrom, transto, count
        );
    }

}
