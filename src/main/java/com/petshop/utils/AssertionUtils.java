package com.petshop.utils;

import io.restassured.response.Response;
import org.testng.Assert;

/**
 * Custom Assertion Utility class
 * Follows Single Responsibility Principle - Handles only assertions
 */
public class AssertionUtils {

    private AssertionUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Assert status code
     * @param response Response object
     * @param expectedStatusCode Expected status code
     */
    public static void assertStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Status code mismatch. Expected: " + expectedStatusCode + ", Actual: " + actualStatusCode);
    }

    /**
     * Assert response time
     * @param response Response object
     * @param maxTimeInMillis Maximum allowed time in milliseconds
     */
    public static void assertResponseTime(Response response, long maxTimeInMillis) {
        long actualTime = response.getTime();
        Assert.assertTrue(actualTime <= maxTimeInMillis,
                "Response time exceeded. Expected: <= " + maxTimeInMillis + "ms, Actual: " + actualTime + "ms");
    }

    /**
     * Assert response body contains text
     * @param response Response object
     * @param expectedText Expected text
     */
    public static void assertResponseContains(Response response, String expectedText) {
        String responseBody = response.asString();
        Assert.assertTrue(responseBody.contains(expectedText),
                "Response body doesn't contain expected text: " + expectedText);
    }

    /**
     * Assert response body equals
     * @param response Response object
     * @param expectedBody Expected body
     */
    public static void assertResponseBodyEquals(Response response, String expectedBody) {
        String actualBody = response.asString();
        Assert.assertEquals(actualBody, expectedBody, "Response body mismatch");
    }

    /**
     * Assert content type
     * @param response Response object
     * @param expectedContentType Expected content type
     */
    public static void assertContentType(Response response, String expectedContentType) {
        String actualContentType = response.getContentType();
        Assert.assertTrue(actualContentType.contains(expectedContentType),
                "Content type mismatch. Expected: " + expectedContentType + ", Actual: " + actualContentType);
    }

    /**
     * Assert header exists
     * @param response Response object
     * @param headerName Header name
     */
    public static void assertHeaderExists(Response response, String headerName) {
        Assert.assertNotNull(response.getHeader(headerName),
                "Header not found: " + headerName);
    }

    /**
     * Assert header value
     * @param response Response object
     * @param headerName Header name
     * @param expectedValue Expected value
     */
    public static void assertHeaderValue(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        Assert.assertEquals(actualValue, expectedValue,
                "Header value mismatch for " + headerName);
    }

    /**
     * Assert JSON path exists
     * @param response Response object
     * @param jsonPath JSON path
     */
    public static void assertJsonPathExists(Response response, String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(value, "JSON path not found: " + jsonPath);
    }

    /**
     * Assert JSON path value
     * @param response Response object
     * @param jsonPath JSON path
     * @param expectedValue Expected value
     */
    public static void assertJsonPathValue(Response response, String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        Assert.assertEquals(actualValue, expectedValue,
                "JSON path value mismatch for " + jsonPath);
    }
}

