package com.lumen.minuAPI.OpenAPI.GPT;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@SuppressWarnings("unchecked")
public class GPTService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public GPTDTO fetchGPTData(
        String openaiKey, String prompt, String model, int maxTokens
    ) {
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiKey);
        headers.set("Content-Type", "application/json");

        // 요청 본문 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(
            Map.of(
                "role", "user",
                "content", prompt
            )
        ));
        requestBody.put("max_completion_tokens", maxTokens);

        // HTTP 요청 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // OpenAI API 호출
        ResponseEntity<Map> response = restTemplate.exchange(
            OPENAI_API_URL,
            HttpMethod.POST,
            entity,
            Map.class
        );

        return parseResponse(response);
    }

    public GPTDTO chatWithHistory(
        String openaiKey, List<GPTMessageDTO> messages, String model, int maxTokens
    ) {
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiKey);
        headers.set("Content-Type", "application/json");

        // 요청 본문 생성 - 전체 메시지 히스토리 포함
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        
        // GPTMessageDTO를 Map으로 변환
        List<Map<String, String>> messageList = messages.stream()
            .map(msg -> Map.of(
                "role", msg.getRole(),
                "content", msg.getContent()
            ))
            .toList();
        
        requestBody.put("messages", messageList);
        requestBody.put("max_completion_tokens", maxTokens);

        // HTTP 요청 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // OpenAI API 호출
        ResponseEntity<Map> response = restTemplate.exchange(
            OPENAI_API_URL,
            HttpMethod.POST,
            entity,
            Map.class
        );

        return parseResponse(response);
    }

    // 응답 파싱 로직 분리
    private GPTDTO parseResponse(ResponseEntity<Map> response) {
        Map<String, Object> responseData = new HashMap<>();
        
        Optional.ofNullable(response.getBody()).ifPresent(body -> {
            // 기본 정보 추출
            responseData.put("id", body.get("id"));
            responseData.put("model", body.get("model"));
            responseData.put("created", body.get("created"));
            responseData.put("usage", body.get("usage"));
            
            // choices에서 메시지 내용 추출
            extractMessageContent(body, responseData);
        });

        return new GPTDTO(responseData);
    }

    // 메시지 내용 추출 로직 분리
    private void extractMessageContent(Map<String, Object> body, Map<String, Object> responseData) {
        Optional.ofNullable((List<Map<String, Object>>) body.get("choices"))
            .filter(choices -> !choices.isEmpty())
            .map(choices -> choices.get(0))
            .ifPresent(firstChoice -> {
                responseData.put("finish_reason", firstChoice.get("finish_reason"));
                
                Optional.ofNullable((Map<String, Object>) firstChoice.get("message"))
                    .ifPresent(message -> {
                        responseData.put("content", message.get("content"));
                        responseData.put("role", message.get("role"));
                    });
            });
    }
}
