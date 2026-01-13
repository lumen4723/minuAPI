package com.lumen.minuAPI.Kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;
import java.util.List;

import com.lumen.minuAPI.OpenAPI.GPT.GPTController;


@RestController
@RequestMapping("/kakao")
public class KakaoController {

    @Autowired
    private GPTController gptController;

    // 카카오 응답 메서드
    private Map<String, Object> textResponse(String text) {
        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", List.of(
                    Map.of(
                        "simpleText", Map.of(
                            "text", text
                        )
                    )
                )
            )
        );
    }

    @PostMapping("/test")
    public Map<String, Object> test(@RequestBody Map<String, Object> body) {
        return textResponse("받은 메시지: " + body.toString());
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/gpt")
    public Map<String, Object> gpt(@RequestBody Map<String, Object> body) {

        // utterance 추출 (에러 시 바로 응답 반환)
        String utterance = java.util.Optional.ofNullable(body.get("userRequest"))
            .filter(obj -> obj instanceof Map)
            .map(obj -> (Map<String, Object>) obj)
            .map(req -> req.get("utterance"))
            .filter(obj -> obj instanceof String)
            .map(obj -> (String) obj)
            .orElse(null);
        
        // !gpt 접두어 제거, 만약 없다면 응답하지 않음
        if (utterance == null || !utterance.startsWith("!gpt ")) { return null; }
        
        String userPrompt = utterance.substring(5).trim();

        // 콜백 URL이 없으면 동기 처리 (기존 방식, 타임아웃 위험)
        String gptResponse = java.util.Optional.ofNullable(
            gptController.getGPTData(userPrompt, "gpt-3.5-turbo", 300))
            .map(res -> res.getBody())
            .map(dto -> dto.getData())
            .map(data -> data.get("content"))
            .map(Object::toString)
            .orElse("GPT 응답을 가져올 수 없습니다.");

        return textResponse(gptResponse);
    }
}
