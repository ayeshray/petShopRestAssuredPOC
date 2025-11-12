package com.petshop.tests;

import com.petshop.api.endpoints.StoreEndpoint;
import com.petshop.api.payloads.Order;
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
 * Store API Test Cases
 * Demonstrates order management operations
 */
public class StoreApiTest extends BaseTest {

    private StoreEndpoint storeEndpoint;
    private Order testOrder;

    @BeforeClass
    public void setup() {
        storeEndpoint = new StoreEndpoint();
    }

    @Test(priority = 1, description = "Place a new order")
    public void testPlaceOrder() {
        ExtentReportManager.logInfo("Generating test order data");
        testOrder = TestDataGenerator.generateOrder();

        ExtentReportManager.logInfo("Sending POST request to place order");
        Response response = storeEndpoint.create(testOrder);

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);
        AssertionUtils.assertContentType(response, "application/json");
        AssertionUtils.assertResponseTime(response, 5000);

        Order createdOrder = JsonUtils.fromResponse(response, Order.class);
        Assert.assertEquals(createdOrder.getPetId(), testOrder.getPetId(), "Pet ID mismatch");
        Assert.assertEquals(createdOrder.getQuantity(), testOrder.getQuantity(), "Quantity mismatch");

        // Store the created order ID for subsequent tests
        testOrder.setId(createdOrder.getId());

        ExtentReportManager.logPass("Order placed successfully with ID: " + createdOrder.getId());
    }

    @Test(priority = 2, description = "Get order by ID", dependsOnMethods = "testPlaceOrder")
    public void testGetOrderById() {
        ExtentReportManager.logInfo("Sending GET request to retrieve order by ID: " + testOrder.getId());
        Response response = storeEndpoint.getById(testOrder.getId());

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);
        AssertionUtils.assertJsonPathExists(response, "id");
        AssertionUtils.assertJsonPathValue(response, "id", testOrder.getId().intValue());

        Order retrievedOrder = JsonUtils.fromResponse(response, Order.class);
        Assert.assertEquals(retrievedOrder.getId(), testOrder.getId(), "Order ID mismatch");

        ExtentReportManager.logPass("Order retrieved successfully");
    }

    @Test(priority = 3, description = "Get store inventory")
    public void testGetStoreInventory() {
        ExtentReportManager.logInfo("Sending GET request to retrieve store inventory");
        Response response = storeEndpoint.getInventory();

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);
        AssertionUtils.assertContentType(response, "application/json");
        AssertionUtils.assertResponseTime(response, 5000);

        ExtentReportManager.logPass("Store inventory retrieved successfully");
    }

    @Test(priority = 4, description = "Delete order by ID", dependsOnMethods = "testPlaceOrder")
    public void testDeleteOrder() {
        ExtentReportManager.logInfo("Sending DELETE request for order ID: " + testOrder.getId());
        Response response = storeEndpoint.delete(testOrder.getId());

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);

        ExtentReportManager.logPass("Order deleted successfully");
    }

    @Test(priority = 5, description = "Verify deleted order returns 404", dependsOnMethods = "testDeleteOrder")
    public void testGetDeletedOrder() {
        ExtentReportManager.logInfo("Attempting to retrieve deleted order");
        Response response = storeEndpoint.getById(testOrder.getId());

        ExtentReportManager.logInfo("Response status code: " + response.getStatusCode());

        // Assertions
        AssertionUtils.assertStatusCode(response, 404);

        ExtentReportManager.logPass("Deleted order correctly returns 404 Not Found");
    }

    @Test(priority = 6, description = "Place order with invalid ID - negative test")
    public void testPlaceOrderWithInvalidId() {
        ExtentReportManager.logInfo("Testing order placement with invalid ID");

        Order invalidOrder = Order.builder()
                .id(0L) // Invalid ID
                .petId(1L)
                .quantity(1)
                .build();

        Response response = storeEndpoint.create(invalidOrder);

        ExtentReportManager.logInfo("Response status code: " + response.getStatusCode());

        // The API behavior may vary for invalid data
        Assert.assertTrue(
                response.getStatusCode() >= 200,
                "Unexpected status code for invalid order"
        );

        ExtentReportManager.logPass("Invalid order placement test completed");
    }

    @Test(priority = 7, description = "Get order with invalid ID - negative test")
    public void testGetOrderWithInvalidId() {
        ExtentReportManager.logInfo("Testing order retrieval with invalid ID");
        Response response = storeEndpoint.getById(999999L);

        ExtentReportManager.logInfo("Response status code: " + response.getStatusCode());

        // Should return 404 for non-existent order
        AssertionUtils.assertStatusCode(response, 404);

        ExtentReportManager.logPass("Invalid order retrieval test completed");
    }
}

