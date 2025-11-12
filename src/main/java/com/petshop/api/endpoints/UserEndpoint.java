package com.petshop.api.endpoints;

import com.petshop.api.payloads.User;
import com.petshop.api.specifications.RequestSpecifications;
import com.petshop.config.ConfigFactory;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * User API Endpoint class
 * Follows Single Responsibility Principle - Handles only User related API calls
 */
public class UserEndpoint implements IApiEndpoint<User> {

    private final String basePath;

    public UserEndpoint() {
        this.basePath = ConfigFactory.getConfig().userEndpoint();
    }

    @Override
    public Response create(User payload) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .body(payload)
                .when()
                .post(basePath);
    }

    @Override
    public Response getById(Object username) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .pathParam("username", username)
                .when()
                .get(basePath + "/{username}");
    }

    @Override
    public Response update(User payload) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .pathParam("username", payload.getUsername())
                .body(payload)
                .when()
                .put(basePath + "/{username}");
    }

    @Override
    public Response delete(Object username) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .pathParam("username", username)
                .when()
                .delete(basePath + "/{username}");
    }

    /**
     * Create multiple users with array
     * @param users List of users
     * @return Response
     */
    public Response createWithArray(List<User> users) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .body(users)
                .when()
                .post(basePath + "/createWithArray");
    }

    /**
     * Create multiple users with list
     * @param users List of users
     * @return Response
     */
    public Response createWithList(List<User> users) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .body(users)
                .when()
                .post(basePath + "/createWithList");
    }

    /**
     * User login
     * @param username Username
     * @param password Password
     * @return Response
     */
    public Response login(String username, String password) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get(basePath + "/login");
    }

    /**
     * User logout
     * @return Response
     */
    public Response logout() {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .when()
                .get(basePath + "/logout");
    }
}

