package com.lumen.minuAPI.OpenAPI.GPT;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GPTRequestDTO {
    private List<GPTMessageDTO> messages;           // 대화 기록
    private String model = "gpt-5.2-chat-latest";   // 기본 모델
    private Integer maxTokens = 500;                // 기본 토큰
}
