# PetShop REST Assured API Testing Framework

## 📋 Table of Contents
- [Overview](#overview)
- [Framework Architecture](#framework-architecture)
- [Folder Structure](#folder-structure)
- [Technologies & Dependencies](#technologies--dependencies)
- [Design Principles](#design-principles)
- [Framework Features](#framework-features)
- [Setup Instructions](#setup-instructions)
- [Running Tests](#running-tests)
- [Configuration](#configuration)
- [Reporting](#reporting)
- [Best Practices](#best-practices)
- [Contributing](#contributing)

---

## 🎯 Overview

This is a robust, scalable, and maintainable REST API testing framework built using **REST Assured** and **TestNG** for the PetShop Swagger API. The framework follows industry best practices and SOLID principles to ensure high code quality and maintainability.

### Key Highlights
- ✅ **Maven-based** project structure
- ✅ **REST Assured** for API testing
- ✅ **TestNG** for test management and assertions
- ✅ **SOLID principles** implementation
- ✅ **Page Object Model (POM)** equivalent for APIs
- ✅ **Extent Reports** for beautiful HTML reporting
- ✅ **Log4j2** for comprehensive logging
- ✅ **Java Faker** for dynamic test data generation
- ✅ **Reusable components** and utilities

---

## 🏗️ Framework Architecture

The framework follows a **layered architecture** pattern:

```
┌─────────────────────────────────────┐
│        Test Layer (TestNG)          │
│   - PetApiTest                      │
│   - UserApiTest                     │
│   - StoreApiTest                    │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│      API Endpoint Layer             │
│   - PetEndpoint                     │
│   - UserEndpoint                    │
│   - StoreEndpoint                   │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│    Payload/POJO Layer               │
│   - Pet, User, Order                │
│   - Request/Response Models         │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│    Specifications Layer             │
│   - RequestSpecifications           │
│   - ResponseSpecifications          │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│    Utilities & Support Layer        │
│   - Config, Logging, Reporting      │
│   - Test Data Generation            │
│   - Assertions, JSON Utils          │
└─────────────────────────────────────┘
```

---

## 📁 Folder Structure

```
petShopRestAssuredPOC/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── petshop/
│   │               ├── api/
│   │               │   ├── endpoints/          # API endpoint classes
│   │               │   │   ├── IApiEndpoint.java
│   │               │   │   ├── PetEndpoint.java
│   │               │   │   ├── UserEndpoint.java
│   │               │   │   └── StoreEndpoint.java
│   │               │   │
│   │               │   ├── payloads/           # POJO/Model classes
│   │               │   │   ├── Pet.java
│   │               │   │   ├── User.java
│   │               │   │   ├── Order.java
│   │               │   │   └── ApiResponse.java
│   │               │   │
│   │               │   └── specifications/     # Request/Response specs
│   │               │       ├── RequestSpecifications.java
│   │               │       └── ResponseSpecifications.java
│   │               │
│   │               ├── config/                 # Configuration management
│   │               │   ├── ConfigManager.java
│   │               │   └── ConfigFactory.java
│   │               │
│   │               └── utils/                  # Utility classes
│   │                   ├── TestDataGenerator.java
│   │                   ├── ExtentReportManager.java
│   │                   ├── JsonUtils.java
│   │                   ├── AssertionUtils.java
│   │                   └── LoggerUtil.java
│   │
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── petshop/
│       │           ├── base/                   # Base test classes
│       │           │   └── BaseTest.java
│       │           │
│       │           ├── listeners/              # TestNG listeners
│       │           │   ├── TestListener.java
│       │           │   └── RetryAnalyzer.java
│       │           │
│       │           └── tests/                  # Test classes
│       │               ├── PetApiTest.java
│       │               ├── UserApiTest.java
│       │               └── StoreApiTest.java
│       │
│       └── resources/
│           ├── config.properties              # Configuration properties
│           └── log4j2.xml                     # Logging configuration
│
├── pom.xml                                    # Maven configuration
├── testng.xml                                 # TestNG suite configuration
├── .gitignore                                 # Git ignore file
└── README.md                                  # This file
```

---

## 🛠️ Technologies & Dependencies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 11+ | Programming Language |
| **Maven** | 3.6+ | Build & Dependency Management |
| **REST Assured** | 5.3.2 | API Testing Library |
| **TestNG** | 7.8.0 | Test Management & Assertions |
| **Jackson** | 2.15.3 | JSON Serialization/Deserialization |
| **Extent Reports** | 5.1.1 | HTML Reporting |
| **Log4j2** | 2.20.0 | Logging Framework |
| **Java Faker** | 1.0.2 | Test Data Generation |
| **Lombok** | 1.18.30 | Reduce Boilerplate Code |
| **Owner** | 1.0.12 | Configuration Management |

### Maven Dependencies Highlights

```xml
<!-- REST Assured Core -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.2</version>
</dependency>

<!-- TestNG -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.0</version>
</dependency>

<!-- Extent Reports -->
<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>5.1.1</version>
</dependency>
```

---

## 🎨 Design Principles

The framework strictly follows **SOLID principles**:

### 1. **Single Responsibility Principle (SRP)**
- Each class has one specific purpose
- Example: `PetEndpoint` handles only Pet API operations
- Example: `TestDataGenerator` handles only test data generation

### 2. **Open/Closed Principle (OCP)**
- Classes are open for extension but closed for modification
- Example: `IApiEndpoint` interface allows new endpoint implementations

### 3. **Liskov Substitution Principle (LSP)**
- Derived classes can substitute base classes
- Example: All endpoint classes implement `IApiEndpoint` interface

### 4. **Interface Segregation Principle (ISP)**
- Specific interfaces for different concerns
- Example: `ConfigManager` interface for configuration
- Example: `IApiEndpoint` interface for API operations

### 5. **Dependency Inversion Principle (DIP)**
- Depend on abstractions, not concrete implementations
- Example: Tests depend on endpoint interfaces
- Example: Configuration through `ConfigFactory`

---

## ✨ Framework Features

### 1. **Configuration Management**
- Centralized configuration using `config.properties`
- Type-safe configuration access using Owner library
- Environment-specific configurations

### 2. **Request/Response Specifications**
- Reusable request specifications with common headers
- Response specifications for validation
- Support for different content types

### 3. **POJO/Payload Management**
- Clean POJO classes using Lombok
- JSON serialization/deserialization using Jackson
- Support for nested objects

### 4. **API Endpoint Classes**
- Separation of concerns - one class per API domain
- Implementation of common CRUD operations
- Additional specific operations per endpoint

### 5. **Test Data Generation**
- Dynamic test data using Java Faker
- Realistic and randomized test data
- Helper methods for specific scenarios

### 6. **Logging**
- Comprehensive logging using Log4j2
- Request/Response logging
- File and console appenders
- Configurable log levels

### 7. **Reporting**
- Beautiful HTML reports using Extent Reports
- Test execution timeline
- Pass/Fail statistics
- Detailed error messages and stack traces

### 8. **Utilities**
- JSON utilities for serialization/deserialization
- Custom assertion utilities
- Logger utilities
- Test data generators

### 9. **TestNG Integration**
- Test prioritization
- Test dependencies
- Parallel execution support
- Retry failed tests
- Custom listeners for enhanced reporting

### 10. **Best Practices**
- Clean code structure
- Meaningful variable/method names
- Proper exception handling
- Code reusability
- Scalable architecture

---

## 🚀 Setup Instructions

### Prerequisites
- **Java JDK 11** or higher installed
- **Maven 3.6+** installed
- **IDE** (IntelliJ IDEA / Eclipse / VS Code)

### Installation Steps

1. **Clone the repository**
```bash
git clone <repository-url>
cd petShopRestAssuredPOC
```

2. **Install dependencies**
```bash
mvn clean install -DskipTests
```

3. **Verify installation**
```bash
mvn clean compile
```

4. **Configure IDE**
- Import as Maven project
- Enable annotation processing (for Lombok)
- Set Java 11 as project SDK

---

## 🏃 Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test class
```bash
mvn test -Dtest=PetApiTest
```

### Run specific test method
```bash
mvn test -Dtest=PetApiTest#testCreatePet
```

### Run tests from testng.xml
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Run tests in parallel
```bash
mvn clean test -Dparallel=classes -DthreadCount=3
```

### Run with specific environment
```bash
mvn clean test -Denvironment=QA
```

---

## ⚙️ Configuration

### config.properties
Located at: `src/test/resources/config.properties`

```properties
# Base URL Configuration
base.url=https://petstore.swagger.io/v2

# API Endpoints
endpoint.pet=/pet
endpoint.store=/store
endpoint.user=/user

# Test Configuration
default.timeout=30
environment=QA

# Logging Configuration
log.level=INFO

# Report Configuration
report.path=test-output/ExtentReports/
report.name=API_Test_Report.html
```

### Accessing Configuration
```java
String baseUrl = ConfigFactory.getConfig().baseUrl();
String petEndpoint = ConfigFactory.getConfig().petEndpoint();
```

---

## 📊 Reporting

### Extent Reports
- Location: `test-output/ExtentReports/API_Test_Report.html`
- Features:
  - Test execution summary
  - Pass/Fail/Skip statistics
  - Detailed test steps
  - Error screenshots (if applicable)
  - Execution timeline
  - System information

### TestNG Reports
- Location: `test-output/index.html`
- Standard TestNG HTML reports

### Logs
- Location: `logs/api-test.log`
- Rolling file appender
- Configurable log levels

---

## 📝 Best Practices

### 1. **Writing Tests**
```java
@Test(priority = 1, description = "Create a new pet")
public void testCreatePet() {
    // Arrange
    Pet pet = TestDataGenerator.generatePet();
    
    // Act
    Response response = petEndpoint.create(pet);
    
    // Assert
    AssertionUtils.assertStatusCode(response, 200);
    ExtentReportManager.logPass("Pet created successfully");
}
```

### 2. **Using Request Specifications**
```java
Response response = given()
    .spec(RequestSpecifications.getBasicRequestSpec())
    .body(payload)
    .when()
    .post(endpoint);
```

### 3. **Logging**
```java
ExtentReportManager.logInfo("Executing test step");
LoggerUtil.info("Test information");
```

### 4. **Assertions**
```java
AssertionUtils.assertStatusCode(response, 200);
AssertionUtils.assertJsonPathValue(response, "name", "doggie");
```

### 5. **Test Data**
```java
Pet pet = TestDataGenerator.generatePet();
User user = TestDataGenerator.generateUser();
```

---

## 🎯 Sample Test Execution Flow

1. **Test Suite Initialization** (`@BeforeSuite`)
   - Initialize Extent Reports
   - Configure REST Assured

2. **Test Execution** (per test)
   - Create Extent Report test
   - Log test start
   - Execute API call
   - Validate response
   - Log results

3. **Test Suite Completion** (`@AfterSuite`)
   - Flush Extent Reports
   - Generate final report

---

## 🔍 API Endpoints Covered

### Pet API
- ✅ Create Pet (POST)
- ✅ Get Pet by ID (GET)
- ✅ Update Pet (PUT)
- ✅ Delete Pet (DELETE)
- ✅ Find Pets by Status (GET)
- ✅ Find Pets by Tags (GET)

### User API
- ✅ Create User (POST)
- ✅ Get User by Username (GET)
- ✅ Update User (PUT)
- ✅ Delete User (DELETE)
- ✅ User Login (GET)
- ✅ User Logout (GET)
- ✅ Create Users with Array (POST)

### Store API
- ✅ Place Order (POST)
- ✅ Get Order by ID (GET)
- ✅ Delete Order (DELETE)
- ✅ Get Store Inventory (GET)

---

## 🤝 Contributing

### Guidelines
1. Follow SOLID principles
2. Write clean, readable code
3. Add proper comments and documentation
4. Write meaningful commit messages
5. Ensure all tests pass before committing
6. Update README for new features

### Code Style
- Use camelCase for variables and methods
- Use PascalCase for class names
- Follow Java naming conventions
- Keep methods small and focused

---

## 📞 Support

For issues or questions:
- Create an issue in the repository
- Contact the development team

---

## 📄 License

This project is created for educational and testing purposes.

---

## 🙏 Acknowledgments

- PetStore Swagger API for providing the test API
- REST Assured community
- TestNG community
- All open-source contributors

---

## 📈 Future Enhancements

- [ ] Integration with CI/CD (Jenkins/GitHub Actions)
- [ ] Allure Reporting integration
- [ ] API contract testing with JSON Schema validation
- [ ] Performance testing with REST Assured
- [ ] Database validation
- [ ] Mock server integration
- [ ] Docker containerization
- [ ] API security testing

---

**Happy Testing! 🚀**

---

*Last Updated: 2024*

