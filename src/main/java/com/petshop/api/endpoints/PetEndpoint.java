package com.petshop.api.endpoints;

import com.petshop.api.payloads.Pet;
import com.petshop.api.specifications.RequestSpecifications;
import com.petshop.config.ConfigFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Pet API Endpoint class
 * Follows Single Responsibility Principle - Handles only Pet related API calls
 * Follows Open/Closed Principle - Open for extension through inheritance
 */
public class PetEndpoint implements IApiEndpoint<Pet> {

    private final String basePath;

    public PetEndpoint() {
        this.basePath = ConfigFactory.getConfig().petEndpoint();
    }

    @Override
    public Response create(Pet payload) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .body(payload)
                .when()
                .post(basePath);
    }

    @Override
    public Response getById(Object id) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .pathParam("petId", id)
                .when()
                .get(basePath + "/{petId}");
    }

    @Override
    public Response update(Pet payload) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .body(payload)
                .when()
                .put(basePath);
    }

    @Override
    public Response delete(Object id) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .pathParam("petId", id)
                .when()
                .delete(basePath + "/{petId}");
    }

    /**
     * Find pets by status
     * @param status Pet status (available, pending, sold)
     * @return Response
     */
    public Response findByStatus(String status) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .queryParam("status", status)
                .when()
                .get(basePath + "/findByStatus");
    }

    /**
     * Find pets by tags
     * @param tags Array of tags
     * @return Response
     */
    public Response findByTags(String[] tags) {
        return given()
                .spec(RequestSpecifications.getBasicRequestSpec())
                .queryParam("tags", String.join(",", tags))
                .when()
                .get(basePath + "/findByTags");
    }

    /**
     * Upload pet image
     * @param petId Pet ID
     * @param filePath Image file path
     * @return Response
     */
    public Response uploadImage(Long petId, String filePath) {
        return given()
                .spec(RequestSpecifications.getMultipartRequestSpec())
                .pathParam("petId", petId)
                .multiPart("file", filePath)
                .when()
                .post(basePath + "/{petId}/uploadImage");
    }
}

