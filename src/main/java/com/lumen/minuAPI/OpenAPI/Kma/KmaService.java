package com.lumen.minuAPI.OpenAPI.Kma;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KmaService {

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public KmaDTO fetchKmaData(
        String apiKey, String ymd, String hm,
        int localnum, boolean help
    ) {
        String baseUrl = "https://apihub.kma.go.kr/api/typ01/url/kma_sfctm2.php";
        String url = buildUrl(baseUrl, apiKey, ymd, hm, localnum, help);

        try {
            String result = restTemplate.getForObject(url, String.class);
            result = KmaUtil.jsontransform(result, help);
            Map<String, Object> jsonResponse = new ObjectMapper().readValue(result, Map.class);

            return new KmaDTO(jsonResponse);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new KmaDTO(
                (Map<String, Object>) new JSONObject()
                    .put("error", "Failed to fetch data from KMA API")
                    .put("message", e.getMessage())
            );
        }
    }

    private String buildUrl(
        String baseUrl, String apiKey, String ymd,
        String hm, int localnum, boolean help
    ) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl)
                .append("?authKey=").append(apiKey)
                .append("&stn=").append(localnum)
                .append("&help=").append(help ? 1 : 0);

        if (ymd != null && !ymd.isEmpty()) {
            urlBuilder.append("&tm=").append(ymd).append(hm);
        }

        return urlBuilder.toString();
    }

}
