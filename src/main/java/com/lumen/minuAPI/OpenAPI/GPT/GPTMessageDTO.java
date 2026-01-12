package com.lumen.minuAPI.OpenAPI.GPT;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GPTMessageDTO {
    private String role;    // "system", "user", "assistant"
    private String content; // 메시지 내용
}
