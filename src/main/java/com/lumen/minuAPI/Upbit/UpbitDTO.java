package com.lumen.minuAPI.Upbit;

import java.util.Map;

public class UpbitDTO {
    private Map<String, Object> data;

    public UpbitDTO(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
