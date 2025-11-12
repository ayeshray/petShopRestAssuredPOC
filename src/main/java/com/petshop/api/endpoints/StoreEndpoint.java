package com.petshop.api.endpoints;

import com.petshop.api.payloads.Order;
import com.petshop.api.specifications.RequestSpecifications;
import com.petshop.config.ConfigFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Store API Endpoint class
 * Follows Single Responsibility Principle - Handles only Store/Order related API calls
 */
public class StoreEndpoint implements IApiEndpoint<Order> {

    private final String basePath;

    public StoreEndpoint() {
        this.basePath = ConfigFactory.getConfig().storeEndpoint();
    }

    @Override
    public Response create(Order payload) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .body(payload)
                .when()
                .post(basePath + "/order");
    }

    @Override
    public Response getById(Object orderId) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .pathParam("orderId", orderId)
                .when()
                .get(basePath + "/order/{orderId}");
    }

    @Override
    public Response update(Order payload) {
        // Store API doesn't support update operation
        throw new UnsupportedOperationException("Update operation is not supported for Store endpoint");
    }

    @Override
    public Response delete(Object orderId) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .pathParam("orderId", orderId)
                .when()
                .delete(basePath + "/order/{orderId}");
    }

    /**
     * Get inventory by status
     * @return Response
     */
    public Response getInventory() {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .when()
                .get(basePath + "/inventory");
    }
}

