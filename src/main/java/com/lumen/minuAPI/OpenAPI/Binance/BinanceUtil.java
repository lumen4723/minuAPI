package com.lumen.minuAPI.OpenAPI.Binance;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BinanceUtil {
    
    private static final String[] KLINE_KEYS = {
        "openTime", "open", "high", "low", "close",
        "volume", "closeTime", "quoteAssetVolume", "numberOfTrades",
        "takerBuyBaseAssetVolume", "takerBuyQuoteAssetVolume", "ignore"
    };

    public static String jsontransform(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> result = new HashMap<>();
            result.put("result", convertKlinesToMap(mapper.readValue(json, List.class)));
            
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static List<Map<String, Object>> convertKlinesToMap(List<List<Object>> klines) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (List<Object> arr : klines) {
            Map<String, Object> map = new LinkedHashMap<>();
            for (int i = 0; i < arr.size() && i < KLINE_KEYS.length; i++) {
                map.put(KLINE_KEYS[i], arr.get(i));
            }
            result.add(map);
        }
        return result;
    }
}
