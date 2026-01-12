package com.lumen.minuAPI.OpenAPI.GPT;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GPTDTO {
    private Map<String, Object> data;
}
