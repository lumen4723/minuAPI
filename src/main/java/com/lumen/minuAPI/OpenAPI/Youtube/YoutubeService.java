package com.lumen.minuAPI.OpenAPI.Youtube;

import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class YoutubeService {

    @Value("${api-key.google}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    
    @SuppressWarnings("unchecked")
    public YoutubeDTO fetchYoutubeData(String title, int row) {
        String url = buildUrl(title, row);

        try {
            String result = restTemplate.getForObject(url, String.class);
            Map<String, Object> resultMap = new ObjectMapper().readValue(result, Map.class);
            
            return new YoutubeDTO(resultMap);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new YoutubeDTO(
                Map.of("error", "Failed to fetch data from YouTube API", "message", e.getMessage())
            );
        }
    }

    private String buildUrl(String title, int row) {
        return String.format(
            "https://www.googleapis.com/youtube/v3/search?key=%s&part=snippet&type=video&q=%s&maxResults=%d",
            apiKey, title, row
        );
    }

}
