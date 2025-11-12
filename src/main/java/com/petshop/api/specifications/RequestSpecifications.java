package com.petshop.api.specifications;

import com.petshop.config.ConfigFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Request Specifications Builder
 * Follows Single Responsibility Principle - Handles only request specification creation
 */
public class RequestSpecifications {

    private RequestSpecifications() {
        // Private constructor to prevent instantiation
    }

    /**
     * Create basic request specification with common configurations
     * @return RequestSpecification
     */
    public static RequestSpecification getBasicRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigFactory.getConfig().baseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    /**
     * Create request specification with authentication
     * @param apiKey API Key for authentication
     * @return RequestSpecification
     */
    public static RequestSpecification getAuthRequestSpec(String apiKey) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBasicRequestSpec())
                .addHeader("api_key", apiKey)
                .build();
    }

    /**
     * Create request specification for multipart/form-data
     * @return RequestSpecification
     */
    public static RequestSpecification getMultipartRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigFactory.getConfig().baseUrl())
                .setContentType(ContentType.MULTIPART)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    /**
     * Create request specification with custom base URI
     * @param baseUri Custom base URI
     * @return RequestSpecification
     */
    public static RequestSpecification getCustomBaseUriSpec(String baseUri) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}

