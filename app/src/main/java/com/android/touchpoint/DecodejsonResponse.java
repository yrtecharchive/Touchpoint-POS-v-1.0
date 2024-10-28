package com.android.touchpoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DecodejsonResponse {

    // Method to parse JSON response and return as a Map
    public static Map<String, String> parseResponse(String jsonResponse) {
        Map<String, String> parsedData = new HashMap<>();

        try {
            // Parse the JSON object
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // Extract fields based on the JSON structure
            parsedData.put("status", jsonObject.optString("status", "N/A"));
            parsedData.put("message", jsonObject.optString("message", "N/A"));
            parsedData.put("parking_id", jsonObject.optString("parking_id", "N/A"));
            parsedData.put("id", jsonObject.optString("id", "N/A"));
            parsedData.put("accesstype", jsonObject.optString("accesstype", "N/A"));
            parsedData.put("code", jsonObject.optString("code", "N/A"));
            parsedData.put("gate", jsonObject.optString("gate", "N/A"));
            parsedData.put("vclass", jsonObject.optString("vclass", "N/A"));
            parsedData.put("entry_time", jsonObject.optString("entry_time", "N/A"));
            parsedData.put("paymenttime", jsonObject.optString("paymenttime", "N/A"));
            parsedData.put("Ptime", jsonObject.optString("Ptime", "N/A"));
            parsedData.put("bill", jsonObject.optString("bill", "N/A"));
            parsedData.put("paid_status", jsonObject.optString("paid_status", "N/A"));

        } catch (JSONException e) {
            e.printStackTrace();
            parsedData.put("error", "Error parsing JSON response: " + e.getMessage());
        }

        return parsedData;
    }
}
