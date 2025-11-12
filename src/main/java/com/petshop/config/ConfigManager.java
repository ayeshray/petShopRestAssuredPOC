package com.petshop.config;

import org.aeonbits.owner.Config;

/**
 * Configuration Interface using Owner library
 * Follows Dependency Inversion Principle - Depend on abstraction
 */
@Config.Sources({"classpath:config.properties"})
public interface ConfigManager extends Config {

    @Key("base.url")
    String baseUrl();

    @Key("endpoint.pet")
    String petEndpoint();

    @Key("endpoint.store")
    String storeEndpoint();

    @Key("endpoint.user")
    String userEndpoint();

    @Key("default.timeout")
    @DefaultValue("30")
    int defaultTimeout();

    @Key("environment")
    @DefaultValue("QA")
    String environment();

    @Key("log.level")
    @DefaultValue("INFO")
    String logLevel();

    @Key("report.path")
    @DefaultValue("test-output/ExtentReports/")
    String reportPath();

    @Key("report.name")
    @DefaultValue("API_Test_Report.html")
    String reportName();

    @Key("log.request.details")
    @DefaultValue("true")
    boolean logRequestDetails();

    @Key("log.response.details")
    @DefaultValue("true")
    boolean logResponseDetails();

    @Key("retry.failed.tests")
    @DefaultValue("1")
    int retryFailedTests();
}

