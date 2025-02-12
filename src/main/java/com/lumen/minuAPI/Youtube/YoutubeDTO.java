package com.lumen.minuAPI.Youtube;

import java.util.Map;

public class YoutubeDTO {
    private Map<String, Object> data;

    public YoutubeDTO(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
