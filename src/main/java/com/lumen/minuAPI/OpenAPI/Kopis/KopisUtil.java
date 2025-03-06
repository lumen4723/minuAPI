package com.lumen.minuAPI.OpenAPI.Kopis;

import org.json.JSONArray;
import org.json.JSONObject;

public class KopisUtil {

    public static String jsontransform(JSONObject json) {
        StringBuilder result = new StringBuilder("{\"result\":[");

        try {
            JSONArray dbArray = json.getJSONObject("dbs").getJSONArray("db");

            for (int i = 0; i < dbArray.length(); i++) {
                JSONObject originObj = dbArray.getJSONObject(i);

                // Create a new JSON object with transformed keys
                JSONObject resultObj = new JSONObject();
                resultObj.put("id", originObj.optString("mt20id", ""));
                resultObj.put("title", originObj.optString("prfnm", ""));
                resultObj.put("start", originObj.optString("prfpdfrom", ""));
                resultObj.put("end", originObj.optString("prfpdto", ""));
                resultObj.put("place", originObj.optString("fcltynm", ""));
                resultObj.put("local", originObj.optString("area", ""));
                resultObj.put("category", originObj.optString("genrenm", ""));
                resultObj.put("openrun", originObj.optString("openrun", ""));
                resultObj.put("poster", originObj.optString("poster", ""));
                resultObj.put("state", originObj.optString("prfstate", ""));

                result.append(resultObj.toString()).append(",");
            }

            if (dbArray.length() > 0) {
                result.setLength(result.length() - 1); // Remove the last comma
            }

            result.append("]}");
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Failed to transform KOPIS JSON: " + e.getMessage() + "\"}";
        }
    }

}
