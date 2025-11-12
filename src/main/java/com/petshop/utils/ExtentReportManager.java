package com.petshop.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.petshop.config.ConfigFactory;

import java.io.File;

/**
 * Extent Report Manager for HTML reporting
 * Follows Singleton Pattern and Single Responsibility Principle
 */
public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private ExtentReportManager() {
        // Private constructor to prevent instantiation
    }

    /**
     * Initialize Extent Reports
     */
    public static void initReports() {
        if (extent == null) {
            String reportPath = ConfigFactory.getConfig().reportPath();
            String reportName = ConfigFactory.getConfig().reportName();
            
            // Create report directory if it doesn't exist
            File reportDir = new File(reportPath);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath + reportName);
            sparkReporter.config().setDocumentTitle("PetShop API Test Report");
            sparkReporter.config().setReportName("REST Assured API Automation Results");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Framework", "REST Assured");
            extent.setSystemInfo("Environment", ConfigFactory.getConfig().environment());
            extent.setSystemInfo("Test Type", "API Testing");
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }

    /**
     * Create a test in the report
     * @param testName Test name
     * @param description Test description
     */
    public static void createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
    }

    /**
     * Get the current test instance
     * @return ExtentTest instance
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /**
     * Log info message
     * @param message Message to log
     */
    public static void logInfo(String message) {
        getTest().log(Status.INFO, message);
    }

    /**
     * Log pass message
     * @param message Message to log
     */
    public static void logPass(String message) {
        getTest().log(Status.PASS, message);
    }

    /**
     * Log fail message
     * @param message Message to log
     */
    public static void logFail(String message) {
        getTest().log(Status.FAIL, message);
    }

    /**
     * Log skip message
     * @param message Message to log
     */
    public static void logSkip(String message) {
        getTest().log(Status.SKIP, message);
    }

    /**
     * Log warning message
     * @param message Message to log
     */
    public static void logWarning(String message) {
        getTest().log(Status.WARNING, message);
    }

    /**
     * Flush reports
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Remove test from thread local
     */
    public static void removeTest() {
        extentTest.remove();
    }
}

