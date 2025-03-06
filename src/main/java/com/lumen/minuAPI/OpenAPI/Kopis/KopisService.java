package com.lumen.minuAPI.OpenAPI.Kopis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KopisService {

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public KopisDTO fetchKopisData(
        String apiKey, String start, String end,
        int row, int page, String state, String localnum
    ) {
        String baseUrl = "http://www.kopis.or.kr/openApi/restful/pblprfr";
        String url = buildUrl(baseUrl, apiKey, start, end, row, page, state, localnum);

        try {
            String xmlResponse = restTemplate.getForObject(url, String.class);
            JSONObject jsonResponse = XML.toJSONObject(xmlResponse);
            String transformedJson = KopisUtil.jsontransform(jsonResponse);

            Map<String, Object> resultMap = new ObjectMapper().readValue(transformedJson, Map.class);
            return new KopisDTO(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new KopisDTO(
                (Map<String, Object>) new JSONObject()
                    .put("error", "Failed to fetch data from KOPIS API")
                    .put("message", e.getMessage()));
        }
    }

    private String buildUrl(
        String baseUrl, String apiKey, String start, String end,
        int row, int page, String state, String localnum
    ) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl)
                .append("?service=").append(apiKey)
                .append("&stdate=").append(start)
                .append("&eddate=").append(end)
                .append("&rows=").append(row)
                .append("&cpage=").append(page);

        if (state != null && !state.isEmpty()) {
            urlBuilder.append("&prfstate=").append(state);
        }

        if (localnum != null && !localnum.isEmpty()) {
            urlBuilder.append("&signgucode=").append(localnum);
        }

        return urlBuilder.toString();
    }

}
