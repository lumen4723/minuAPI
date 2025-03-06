package com.lumen.minuAPI.OpenAPI.Youtube;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YoutubeDTO {
    private Map<String, Object> data;
}
