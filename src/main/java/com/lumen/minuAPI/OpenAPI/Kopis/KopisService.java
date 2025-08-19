package com.lumen.minuAPI.OpenAPI.Kopis;

import org.json.XML;
import org.springframework.http.converter.StringHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
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

        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        try {
            String xmlresult = restTemplate.getForObject(url, String.class);
            String jsonresult = KopisUtil.jsontransform(XML.toJSONObject(xmlresult));
            Map<String, Object> resultMap = new ObjectMapper().readValue(jsonresult, Map.class);
            
            return new KopisDTO(resultMap);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new KopisDTO(
                Map.of("error", "Failed to fetch data from Kopis API", "message", e.getMessage())
            );
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
