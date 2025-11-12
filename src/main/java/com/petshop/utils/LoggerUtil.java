package com.petshop.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logger Utility class
 * Follows Single Responsibility Principle - Handles only logging operations
 */
public class LoggerUtil {

    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    private LoggerUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Log info message
     * @param message Message to log
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Log debug message
     * @param message Message to log
     */
    public static void debug(String message) {
        logger.debug(message);
    }

    /**
     * Log error message
     * @param message Message to log
     */
    public static void error(String message) {
        logger.error(message);
    }

    /**
     * Log error message with exception
     * @param message Message to log
     * @param throwable Exception
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Log warning message
     * @param message Message to log
     */
    public static void warn(String message) {
        logger.warn(message);
    }

    /**
     * Log fatal message
     * @param message Message to log
     */
    public static void fatal(String message) {
        logger.fatal(message);
    }

    /**
     * Get logger for specific class
     * @param clazz Class
     * @return Logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}

