package com.lumen.minuAPI.Kopis;

import java.util.Map;

public class KopisDTO {
    private Map<String, Object> data;

    public KopisDTO(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
