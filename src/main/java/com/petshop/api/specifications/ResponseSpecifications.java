package com.petshop.api.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

/**
 * Response Specifications Builder
 * Follows Single Responsibility Principle - Handles only response specification creation
 */
public class ResponseSpecifications {

    private ResponseSpecifications() {
        // Private constructor to prevent instantiation
    }

    /**
     * Create response specification for successful requests (2xx status codes)
     * @return ResponseSpecification
     */
    public static ResponseSpecification getSuccessResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(5000L))
                .build();
    }

    /**
     * Create response specification for created resources (201 status code)
     * @return ResponseSpecification
     */
    public static ResponseSpecification getCreatedResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(5000L))
                .build();
    }

    /**
     * Create response specification for bad requests (400 status code)
     * @return ResponseSpecification
     */
    public static ResponseSpecification getBadRequestResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectResponseTime(lessThan(5000L))
                .build();
    }

    /**
     * Create response specification for not found (404 status code)
     * @return ResponseSpecification
     */
    public static ResponseSpecification getNotFoundResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectResponseTime(lessThan(5000L))
                .build();
    }

    /**
     * Create response specification with custom status code
     * @param statusCode Expected status code
     * @return ResponseSpecification
     */
    public static ResponseSpecification getCustomStatusCodeSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(5000L))
                .build();
    }
}

