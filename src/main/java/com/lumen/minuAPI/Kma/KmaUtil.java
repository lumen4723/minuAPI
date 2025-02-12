package com.lumen.minuAPI.Kma;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class KmaUtil {

    private static final String[] INDEXES = {
            "TM", "STN", "WD", "WS", "GST_WD", "GST_WS", "GST_TM", "PA", "PS", "PT",
            "PR", "TA", "TD", "HM", "PV", "RN", "RN_DAY", "RN_JUN", "RN_INT", "SD_HR3",
            "SD_DAY", "SD_TOT", "WC", "WP", "WW", "CA_TOT", "CA_MID", "CH_MIN", "CT", "CT_TOP",
            "CT_MID", "CT_LOW", "VS", "SS", "SI", "ST_GD", "TS", "TE_005", "TE_01", "TE_02",
            "TE_03", "ST_SEA", "WH", "BF", "IR", "IX"
    };

    public static String jsontransform(String str, boolean help) {
        String[] lines = str.split("\n");
        List<Map<String, Object>> transformedData = new ArrayList<>();

        int captureDescription = 0;
        StringBuilder description = new StringBuilder();

        for (String line : lines) {
            if (line.trim().isEmpty()) { continue; }

            if (help && line.startsWith("#-")) {
                captureDescription++;
                continue;
            }

            if (captureDescription == 2 || captureDescription == 1) {
                description.append(line.replace("#", "").trim()).append("\n");
                continue;
            }

            if (line.startsWith("#")) continue;

            String[] parts = line.trim().split("\\s+");
            Map<String, Object> dataMap = new HashMap<>();

            for (int i = 0; i < INDEXES.length && i < parts.length; i++) {
                dataMap.put(INDEXES[i], parts[i]);
            }

            transformedData.add(dataMap);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            StringBuilder result = new StringBuilder("{");
            if (help) {
                result.append("\"description\":")
                        .append(mapper.writeValueAsString(description.toString()))
                        .append(",");
            }
            result.append("\"result\":")
                    .append(mapper.writeValueAsString(transformedData))
                    .append("}");

            return result.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

}
