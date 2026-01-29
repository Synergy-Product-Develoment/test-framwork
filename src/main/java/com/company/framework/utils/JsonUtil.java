package com.company.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtil(){}

    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    // Additional utility methods can be added here fromjson handling
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON to object", e);
        }
    }

    // Getter for ObjectMapper if needed
    public static ObjectMapper getMapper() {
        return MAPPER;      
    }


    public static String prettyPrint(String json) {
        try {
            Object jsonObj = MAPPER.readValue(json, Object.class);
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to pretty print JSON", e);       
        }
    }
    
}
