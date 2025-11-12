package com.petshop.listeners;

import com.petshop.config.ConfigFactory;
import com.petshop.utils.LoggerUtil;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry Analyzer for failed tests
 * Follows Single Responsibility Principle - Handles test retry logic
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryCount = ConfigFactory.getConfig().retryFailedTests();

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            String testName = result.getMethod().getMethodName();
            LoggerUtil.warn("Retrying test: " + testName + " (Attempt " + retryCount + " of " + maxRetryCount + ")");
            return true;
        }
        return false;
    }
}

