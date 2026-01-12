package com.lumen.minuAPI.OpenAPI.GPT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gpt")
public class GPTController {
    
    @Value("${api-key.openai}")
    private String openaiKey;

    @Autowired
    private GPTService gptService;

    @GetMapping
    public ResponseEntity<GPTDTO> getGPTData(
        @RequestParam String prompt,
        @RequestParam(defaultValue = "gpt-5.2-chat-latest") String model,
        @RequestParam(defaultValue = "500") int maxTokens
    ) {
        return ResponseEntity.ok(
            gptService.fetchGPTData(
                openaiKey, prompt, model, maxTokens
            )
        );
    }

    // 대화 이어가기 (메시지 히스토리 포함)
    @PostMapping
    public ResponseEntity<GPTDTO> chatWithHistory(
        @RequestBody GPTRequestDTO request
    ) {
        return ResponseEntity.ok(
            gptService.chatWithHistory(
                openaiKey,
                request.getMessages(),
                request.getModel() != null ? request.getModel() : "gpt-5.2-chat-latest",
                request.getMaxTokens() != null ? request.getMaxTokens() : 500
            )
        );
    }
}
