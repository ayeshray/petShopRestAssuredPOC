package com.petshop.base;

import com.petshop.listeners.TestListener;
import com.petshop.utils.ExtentReportManager;
import io.restassured.RestAssured;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

/**
 * Base Test class for all test classes
 * Follows DRY principle and provides common setup/teardown
 */
@Listeners(TestListener.class)
public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        // Initialize Extent Reports
        ExtentReportManager.initReports();
        
        // Configure RestAssured
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        // Flush Extent Reports
        ExtentReportManager.flushReports();
    }
}

