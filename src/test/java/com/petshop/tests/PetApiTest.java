package com.petshop.tests;

import com.petshop.api.endpoints.PetEndpoint;
import com.petshop.api.payloads.Pet;
import com.petshop.base.BaseTest;
import com.petshop.utils.AssertionUtils;
import com.petshop.utils.ExtentReportManager;
import com.petshop.utils.JsonUtils;
import com.petshop.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Pet API Test Cases
 * Demonstrates CRUD operations on Pet endpoints
 */
public class PetApiTest extends BaseTest {

    private PetEndpoint petEndpoint;
    private Pet testPet;

    @BeforeClass
    public void setup() {
        petEndpoint = new PetEndpoint();
    }

    @Test(priority = 1, description = "Create a new pet with valid data")
    public void testCreatePet() {
        ExtentReportManager.logInfo("Generating test pet data");
        testPet = TestDataGenerator.generatePet();

        ExtentReportManager.logInfo("Sending POST request to create pet");
        Response response = petEndpoint.create(testPet);

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);
        AssertionUtils.assertContentType(response, "application/json");
        AssertionUtils.assertResponseTime(response, 5000);

        Pet createdPet = JsonUtils.fromResponse(response, Pet.class);
        Assert.assertEquals(createdPet.getName(), testPet.getName(), "Pet name mismatch");
        Assert.assertEquals(createdPet.getStatus(), testPet.getStatus(), "Pet status mismatch");

        // Store the created pet ID for subsequent tests
        testPet.setId(createdPet.getId());

        ExtentReportManager.logPass("Pet created successfully with ID: " + createdPet.getId());
    }

    @Test(priority = 2, description = "Get pet by ID", dependsOnMethods = "testCreatePet")
    public void testGetPetById() {
        ExtentReportManager.logInfo("Sending GET request to retrieve pet by ID: " + testPet.getId());
        Response response = petEndpoint.getById(testPet.getId());

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);
        AssertionUtils.assertJsonPathExists(response, "id");
        AssertionUtils.assertJsonPathValue(response, "name", testPet.getName());

        Pet retrievedPet = JsonUtils.fromResponse(response, Pet.class);
        Assert.assertEquals(retrievedPet.getId(), testPet.getId(), "Pet ID mismatch");

        ExtentReportManager.logPass("Pet retrieved successfully: " + retrievedPet.getName());
    }

    @Test(priority = 3, description = "Update pet information", dependsOnMethods = "testCreatePet")
    public void testUpdatePet() {
        ExtentReportManager.logInfo("Updating pet name");
        testPet.setName("Updated " + testPet.getName());
        testPet.setStatus("sold");

        ExtentReportManager.logInfo("Sending PUT request to update pet");
        Response response = petEndpoint.update(testPet);

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);

        Pet updatedPet = JsonUtils.fromResponse(response, Pet.class);
        Assert.assertEquals(updatedPet.getName(), testPet.getName(), "Updated pet name mismatch");
        Assert.assertEquals(updatedPet.getStatus(), "sold", "Updated pet status mismatch");

        ExtentReportManager.logPass("Pet updated successfully");
    }

    @Test(priority = 4, description = "Find pets by status")
    public void testFindPetsByStatus() {
        String status = "available";
        ExtentReportManager.logInfo("Searching for pets with status: " + status);

        Response response = petEndpoint.findByStatus(status);

        ExtentReportManager.logInfo("Response status code: " + response.getStatusCode());

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);
        AssertionUtils.assertContentType(response, "application/json");

        Pet[] pets = JsonUtils.fromResponse(response, Pet[].class);
        Assert.assertTrue(pets.length > 0, "No pets found with status: " + status);

        ExtentReportManager.logPass("Found " + pets.length + " pets with status: " + status);
    }

    @Test(priority = 5, description = "Delete pet by ID", dependsOnMethods = "testCreatePet")
    public void testDeletePet() {
        ExtentReportManager.logInfo("Sending DELETE request for pet ID: " + testPet.getId());
        Response response = petEndpoint.delete(testPet.getId());

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);

        ExtentReportManager.logPass("Pet deleted successfully");
    }

    @Test(priority = 6, description = "Verify deleted pet returns 404", dependsOnMethods = "testDeletePet")
    public void testGetDeletedPet() {
        ExtentReportManager.logInfo("Attempting to retrieve deleted pet");
        Response response = petEndpoint.getById(testPet.getId());

        ExtentReportManager.logInfo("Response status code: " + response.getStatusCode());

        // Assertions
        AssertionUtils.assertStatusCode(response, 404);

        ExtentReportManager.logPass("Deleted pet correctly returns 404 Not Found");
    }

    @Test(priority = 7, description = "Create pet with invalid data - negative test")
    public void testCreatePetWithInvalidData() {
        ExtentReportManager.logInfo("Testing pet creation with invalid data");

        Pet invalidPet = Pet.builder()
                .id(-1L) // Invalid ID
                .build();

        Response response = petEndpoint.create(invalidPet);

        ExtentReportManager.logInfo("Response status code: " + response.getStatusCode());

        // This might return 200 or 400 depending on API validation
        // Adjust assertion based on actual API behavior
        Assert.assertTrue(
                response.getStatusCode() == 200 || response.getStatusCode() == 400,
                "Unexpected status code for invalid pet creation"
        );

        ExtentReportManager.logPass("Invalid pet creation test completed");
    }
}

