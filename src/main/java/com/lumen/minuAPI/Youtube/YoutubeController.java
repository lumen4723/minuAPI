package com.lumen.minuAPI.Youtube;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class YoutubeController {
    @Value("${api-key.google}")
    private String apiKey;

    @GetMapping("/youtube")
    public ResponseEntity<?> youtube(
        @RequestParam() String title,
        @RequestParam(defaultValue = "10") int row
    ) {
        // basic API URL
        String baseUrl = "https://www.googleapis.com/youtube/v3/search";

        // URL 쿼리 파라미터 구성
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?key=").append(apiKey);
        urlBuilder.append("&part=snippet");
        urlBuilder.append("&type=video");
        urlBuilder.append("&q=").append(title);
        urlBuilder.append("&maxResults=").append(row);
        
        // API 호출
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(urlBuilder.toString(), String.class);

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
                        .put("error", "Failed to fetch data from Youtube API" + e.getMessage())
                );
        }
    }
}
