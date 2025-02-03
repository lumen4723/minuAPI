package com.lumen.minuAPI.Kma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

// https://apihub.kma.go.kr/

@RestController
public class KmaController {
    @Value("${kma.api.key}")
    private String apiKey;

    @GetMapping("/kma")
    public ResponseEntity<?> kma(
        @RequestParam(required = false) String ymd,
        @RequestParam(defaultValue = "0000") String hm,
        @RequestParam(defaultValue = "0") int localnum,
        @RequestParam(defaultValue = "false") boolean help
    ) {
        String baseUrl = "https://apihub.kma.go.kr/api/typ01/url/kma_sfctm2.php";

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?authKey=").append(apiKey);
        urlBuilder.append("&stn=").append(localnum);
        urlBuilder.append("&help=").append(help? 1 : 0);

        if (ymd != null && !ymd.isEmpty()) {
            urlBuilder.append("&tm=").append(ymd + hm);
        }

        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(urlBuilder.toString(), String.class);
            result = kmaJsonTransformer(result, help);

            @SuppressWarnings("unchecked")
            Map<String, Object> jsonResponse = new ObjectMapper().readValue(result, Map.class);

            return ResponseEntity.ok(jsonResponse);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(500)
                .body(
                    new JSONObject()
                        .put("error", "Failed to fetch data from Kma API")
                        .put("message", e.getMessage())
                );
        }
    }

    public static String kmaJsonTransformer(String str, boolean help) {
        String[] indexes = {
            "TM", "STN", "WD", "WS", "GST_WD", "GST_WS", "GST_TM", "PA", "PS", "PT",
            "PR", "TA", "TD", "HM", "PV", "RN", "RN_DAY", "RN_JUN", "RN_INT", "SD_HR3",
            "SD_DAY", "SD_TOT", "WC", "WP", "WW", "CA_TOT", "CA_MID", "CH_MIN", "CT", "CT_TOP",
            "CT_MID", "CT_LOW", "VS", "SS", "SI", "ST_GD", "TS", "TE_005", "TE_01", "TE_02",
            "TE_03", "ST_SEA", "WH", "BF", "IR", "IX"
        };
        String[] lines = str.split("\n");
        List<Map<String, Object>> transformedData = new ArrayList<>();

        int captureDescription = 0;
        String description = "";

        for (String line : lines) {
            // 빈 줄은 건너뜀
            if (line.trim().isEmpty()) { continue; }

            // description 위치 값 추가
            if (help && line.startsWith("#-")) {
                captureDescription++;
                continue;
            }
    
            if (captureDescription == 2 || captureDescription == 1) {
                description += line.replace("#", "").trim() + "\n";
                continue;
            }

            if (line.startsWith("#")) { continue; }

            // 스페이스의 개수의 상관없이 스플릿
            String[] parts = line.trim().split("\\s+");
            
            Map<String, Object> dataMap = new HashMap<>();
            for (int i = 0; i < indexes.length; i++) {
                String value = parts[i];
                dataMap.put(indexes[i], value);
            }

            transformedData.add(dataMap);
        }
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            StringBuilder result = new StringBuilder("{");
            if (help) {
                result.append("\"description\":")
                    .append(mapper.writeValueAsString(description))
                    .append(",");
            }
            result.append("\"result\":")
                .append(mapper.writeValueAsString(transformedData))
                .append("}");

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}

