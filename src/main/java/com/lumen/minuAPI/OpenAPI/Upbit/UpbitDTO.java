package com.lumen.minuAPI.OpenAPI.Upbit;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpbitDTO {
    private Map<String, Object> data;
}
