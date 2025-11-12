package com.petshop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;

/**
 * JSON Utility class for JSON operations
 * Follows Single Responsibility Principle - Handles only JSON operations
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private JsonUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Convert object to JSON string
     * @param object Object to convert
     * @return JSON string
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    /**
     * Convert JSON string to object
     * @param json JSON string
     * @param clazz Target class
     * @param <T> Type parameter
     * @return Object of type T
     */
    public static <T> T fromJsonString(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }

    /**
     * Convert Response to object
     * @param response Response object
     * @param clazz Target class
     * @param <T> Type parameter
     * @return Object of type T
     */
    public static <T> T fromResponse(Response response, Class<T> clazz) {
        return fromJsonString(response.asString(), clazz);
    }

    /**
     * Read JSON from file
     * @param filePath File path
     * @param clazz Target class
     * @param <T> Type parameter
     * @return Object of type T
     */
    public static <T> T readFromFile(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON from file: " + filePath, e);
        }
    }

    /**
     * Write object to JSON file
     * @param object Object to write
     * @param filePath File path
     */
    public static void writeToFile(Object object, String filePath) {
        try {
            objectMapper.writeValue(new File(filePath), object);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write object to file: " + filePath, e);
        }
    }

    /**
     * Pretty print JSON string
     * @param json JSON string
     * @return Pretty printed JSON
     */
    public static String prettyPrint(String json) {
        try {
            Object jsonObject = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            return json;
        }
    }
}

