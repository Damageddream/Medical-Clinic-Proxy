package com.damageddream.MedicalClinicProxy.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonMessageExtractor {
    private static final Pattern jsonPattern = Pattern.compile("\\[\\{.*?\\}\\]");

    public static String extractMessage(String errorBody) {
        Matcher matcher = jsonPattern.matcher(errorBody);
        if (matcher.find()) {
            String jsonPart = matcher.group(0);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonPart);
                JsonNode messageNode = rootNode.get(0).get("message");
                if (messageNode != null) {
                    return messageNode.asText();
                }
            } catch (IOException e) {
                return "Error in parsing error message";
            }
        }
        return null;
    }
}
