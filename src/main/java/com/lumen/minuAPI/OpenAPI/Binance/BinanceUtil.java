package com.lumen.minuAPI.OpenAPI.Binance;

import java.io.IOException;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BinanceUtil {
    
    private static final String[] KLINE_KEYS = {
        "openTime",
        "open",
        "high",
        "low",
        "close",
        "volume",
        "closeTime",
        "quoteAssetVolume",
        "numberOfTrades",
        "takerBuyBaseAssetVolume",
        "takerBuyQuoteAssetVolume",
        "ignore"
    };

    @SuppressWarnings("unchecked")
    public static String jsontransform(String json) {
        try {
            // String을 이중 리스트로 캐스팅
            List<List<Object>> result = new ObjectMapper().readValue(json, List.class);
            List<Map<String, Object>> mapped = convertKlinesToMap(result);
            // 다시 JSON 문자열로 변환
            return new ObjectMapper().writeValueAsString(mapped);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
