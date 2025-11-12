package com.petshop.api.endpoints;

import io.restassured.response.Response;

/**
 * Base interface for API endpoints
 * Follows Interface Segregation Principle and Dependency Inversion Principle
 * @param <T> Payload type
 */
public interface IApiEndpoint<T> {

    /**
     * Create a new resource
     * @param payload Request payload
     * @return Response
     */
    Response create(T payload);

    /**
     * Get resource by ID
     * @param id Resource ID
     * @return Response
     */
    Response getById(Object id);

    /**
     * Update existing resource
     * @param payload Request payload
     * @return Response
     */
    Response update(T payload);

    /**
     * Delete resource by ID
     * @param id Resource ID
     * @return Response
     */
    Response delete(Object id);
}

