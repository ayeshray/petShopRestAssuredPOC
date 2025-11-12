package com.petshop.listeners;

import com.petshop.utils.ExtentReportManager;
import com.petshop.utils.LoggerUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener for test execution events
 * Follows Observer Pattern - Listens to test events
 */
public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        LoggerUtil.info("Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LoggerUtil.info("Test Suite Finished: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        
        LoggerUtil.info("Test Started: " + testName);
        ExtentReportManager.createTest(testName, description != null ? description : testName);
        ExtentReportManager.logInfo("Test execution started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        LoggerUtil.info("Test Passed: " + testName);
        ExtentReportManager.logPass("Test passed successfully: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();
        
        LoggerUtil.error("Test Failed: " + testName, throwable);
        ExtentReportManager.logFail("Test failed: " + testName);
        
        if (throwable != null) {
            ExtentReportManager.logFail("Error: " + throwable.getMessage());
            ExtentReportManager.logFail("Stack Trace: " + getStackTrace(throwable));
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        LoggerUtil.warn("Test Skipped: " + testName);
        ExtentReportManager.createTest(testName, "Test was skipped");
        ExtentReportManager.logSkip("Test skipped: " + testName);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }

    /**
     * Get stack trace as string
     * @param throwable Throwable
     * @return Stack trace string
     */
    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}

