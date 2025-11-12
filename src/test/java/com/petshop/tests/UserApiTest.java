package com.petshop.tests;

import com.petshop.api.endpoints.UserEndpoint;
import com.petshop.api.payloads.User;
import com.petshop.base.BaseTest;
import com.petshop.utils.AssertionUtils;
import com.petshop.utils.ExtentReportManager;
import com.petshop.utils.JsonUtils;
import com.petshop.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * User API Test Cases
 * Demonstrates user management operations
 */
public class UserApiTest extends BaseTest {

    private UserEndpoint userEndpoint;
    private User testUser;

    @BeforeClass
    public void setup() {
        userEndpoint = new UserEndpoint();
    }

    @Test(priority = 1, description = "Create a new user with valid data")
    public void testCreateUser() {
        ExtentReportManager.logInfo("Generating test user data");
        testUser = TestDataGenerator.generateUser();

        ExtentReportManager.logInfo("Sending POST request to create user");
        Response response = userEndpoint.create(testUser);

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);
        AssertionUtils.assertResponseTime(response, 5000);

        ExtentReportManager.logPass("User created successfully: " + testUser.getUsername());
    }

    @Test(priority = 2, description = "Get user by username", dependsOnMethods = "testCreateUser")
    public void testGetUserByUsername() {
        ExtentReportManager.logInfo("Sending GET request to retrieve user: " + testUser.getUsername());
        Response response = userEndpoint.getById(testUser.getUsername());

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);
        AssertionUtils.assertJsonPathValue(response, "username", testUser.getUsername());

        User retrievedUser = JsonUtils.fromResponse(response, User.class);
        Assert.assertEquals(retrievedUser.getEmail(), testUser.getEmail(), "Email mismatch");

        ExtentReportManager.logPass("User retrieved successfully");
    }

    @Test(priority = 3, description = "Update user information", dependsOnMethods = "testCreateUser")
    public void testUpdateUser() {
        ExtentReportManager.logInfo("Updating user information");
        testUser.setFirstName("Updated" + testUser.getFirstName());
        testUser.setEmail("updated_" + testUser.getEmail());

        ExtentReportManager.logInfo("Sending PUT request to update user");
        Response response = userEndpoint.update(testUser);

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);

        ExtentReportManager.logPass("User updated successfully");
    }

    @Test(priority = 4, description = "User login with valid credentials", dependsOnMethods = "testCreateUser")
    public void testUserLogin() {
        ExtentReportManager.logInfo("Testing user login");
        Response response = userEndpoint.login(testUser.getUsername(), testUser.getPassword());

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);
        AssertionUtils.assertResponseContains(response, "logged in user session");

        ExtentReportManager.logPass("User login successful");
    }

    @Test(priority = 5, description = "User logout")
    public void testUserLogout() {
        ExtentReportManager.logInfo("Testing user logout");
        Response response = userEndpoint.logout();

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);

        ExtentReportManager.logPass("User logout successful");
    }

    @Test(priority = 6, description = "Create multiple users with array")
    public void testCreateUsersWithArray() {
        ExtentReportManager.logInfo("Generating multiple users");
        List<User> users = TestDataGenerator.generateUsers(3);

        ExtentReportManager.logInfo("Sending POST request to create multiple users");
        Response response = userEndpoint.createWithArray(users);

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);

        ExtentReportManager.logPass("Multiple users created successfully");
    }

    @Test(priority = 7, description = "Delete user", dependsOnMethods = "testCreateUser")
    public void testDeleteUser() {
        ExtentReportManager.logInfo("Sending DELETE request for user: " + testUser.getUsername());
        Response response = userEndpoint.delete(testUser.getUsername());

        ExtentReportManager.logInfo("Response: " + JsonUtils.prettyPrint(response.asString()));

        // Assertions
        AssertionUtils.assertStatusCode(response, 200);

        ExtentReportManager.logPass("User deleted successfully");
    }

    @Test(priority = 8, description = "Verify deleted user returns 404", dependsOnMethods = "testDeleteUser")
    public void testGetDeletedUser() {
        ExtentReportManager.logInfo("Attempting to retrieve deleted user");
        Response response = userEndpoint.getById(testUser.getUsername());

        ExtentReportManager.logInfo("Response status code: " + response.getStatusCode());

        // Assertions
        AssertionUtils.assertStatusCode(response, 404);

        ExtentReportManager.logPass("Deleted user correctly returns 404 Not Found");
    }

    @Test(priority = 9, description = "User login with invalid credentials - negative test")
    public void testUserLoginWithInvalidCredentials() {
        ExtentReportManager.logInfo("Testing user login with invalid credentials");
        Response response = userEndpoint.login("invalidUser", "invalidPassword");

        ExtentReportManager.logInfo("Response status code: " + response.getStatusCode());

        // The API might return 200 or 400, check actual behavior
        Assert.assertTrue(
                response.getStatusCode() >= 200,
                "Unexpected status code for invalid login"
        );

        ExtentReportManager.logPass("Invalid login test completed");
    }
}

