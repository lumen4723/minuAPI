package com.lumen.minuAPI.Kma;

import java.util.Map;

public class KmaDTO {
    private Map<String, Object> data;

    public KmaDTO(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
