package com.lumen.minuAPI.KOPIS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

@RestController
public class KOPISController {
    @Value("${kopis.api.key}")
    private String apiKey;

    @GetMapping("/kopis")
    public ResponseEntity<?> kopis(
        @RequestParam() String start,
        @RequestParam() String end,
        @RequestParam(defaultValue = "10") int row,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(required = false) String state,
        @RequestParam(required = false) String localnum
    ) {
        // basic API URL
        String baseUrl = "http://www.kopis.or.kr/openApi/restful/pblprfr";

        // URL 쿼리 파라미터 구성
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?service=").append(apiKey);
        urlBuilder.append("&stdate=").append(start);
        urlBuilder.append("&eddate=").append(end);
        urlBuilder.append("&rows=").append(row);
        urlBuilder.append("&cpage=").append(page);

        // 상태 코드가 있는 경우 추가
        if (state != null && !state.isEmpty()) {
            urlBuilder.append("&prfstate=").append(state);
        }

        // 지역 코드가 있는 경우 추가
        if (localnum != null && !localnum.isEmpty()) {
            urlBuilder.append("&signgucode=").append(localnum);
        }

        // API 호출
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(urlBuilder.toString(), String.class);
            result = kopisJsonTransformer(XML.toJSONObject(result));
            // result = jsonPrettier(jsonList);

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
                        .put("error", "Failed to fetch data from KOPIS API")
                        .put("message", e.getMessage())
                );
        }
    }

    public static String kopisJsonTransformer(JSONObject json) {
        StringBuilder result = new StringBuilder("{\"result\":[");
    
        try {
            JSONArray dbArray = json.getJSONObject("dbs").getJSONArray("db");
    
            for (int i = 0; i < dbArray.length(); i++) {
                JSONObject originObj = dbArray.getJSONObject(i);
    
                // Create a new JSON object with transformed keys
                JSONObject resultObj = new JSONObject();
                resultObj.put("id", originObj.getString("mt20id"));
                resultObj.put("title", originObj.getString("prfnm"));
                resultObj.put("start", originObj.getString("prfpdfrom"));
                resultObj.put("end", originObj.getString("prfpdto"));
                resultObj.put("place", originObj.getString("fcltynm"));
                resultObj.put("local", originObj.getString("area"));
                resultObj.put("category", originObj.getString("genrenm"));
                resultObj.put("openrun", originObj.getString("openrun"));
                resultObj.put("poster", originObj.getString("poster"));
                resultObj.put("state", originObj.getString("prfstate"));
    
                // Append the transformed JSON object to the result
                result.append(resultObj.toString()).append(",");
            }
    
            if (dbArray.length() > 0) {
                result.setLength(result.length() - 1); // Remove the last comma
            }

            result.append("]}");
            return result.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Failed to transform KOPIS JSON: " + e.getMessage() + "\"}";
        }
    }
}
