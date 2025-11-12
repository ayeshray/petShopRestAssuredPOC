# Quick Start Guide

This guide will help you get started with the PetShop REST Assured framework in 5 minutes!

## ⚡ Quick Setup (5 minutes)

### Step 1: Prerequisites Check
Ensure you have installed:
```bash
java -version   # Should be Java 11 or higher
mvn -version    # Should be Maven 3.6 or higher
```

### Step 2: Clone and Build
```bash
# Navigate to your project directory
cd petShopRestAssuredPOC

# Install dependencies
mvn clean install -DskipTests
```

### Step 3: Run Your First Test
```bash
# Run all tests
mvn clean test

# Or run a specific test class
mvn test -Dtest=PetApiTest
```

### Step 4: View Reports
After test execution:
- **Extent Report**: Open `test-output/ExtentReports/API_Test_Report.html` in browser
- **TestNG Report**: Open `test-output/index.html` in browser
- **Logs**: Check `logs/api-test.log`

## 🎯 Your First Custom Test

### 1. Create a new test class

Create `src/test/java/com/petshop/tests/MyFirstTest.java`:

```java
package com.petshop.tests;

import com.petshop.api.endpoints.PetEndpoint;
import com.petshop.api.payloads.Pet;
import com.petshop.base.BaseTest;
import com.petshop.utils.AssertionUtils;
import com.petshop.utils.ExtentReportManager;
import com.petshop.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MyFirstTest extends BaseTest {

    private PetEndpoint petEndpoint;

    @BeforeClass
    public void setup() {
        petEndpoint = new PetEndpoint();
    }

    @Test(description = "My first API test")
    public void testCreatePet() {
        // Generate test data
        Pet pet = TestDataGenerator.generatePet();
        
        // Make API call
        Response response = petEndpoint.create(pet);
        
        // Validate response
        AssertionUtils.assertStatusCode(response, 200);
        
        // Log success
        ExtentReportManager.logPass("Pet created successfully!");
    }
}
```

### 2. Add test to testng.xml

Add to `testng.xml`:
```xml
<test name="My First Test">
    <classes>
        <class name="com.petshop.tests.MyFirstTest"/>
    </classes>
</test>
```

### 3. Run your test
```bash
mvn test -Dtest=MyFirstTest
```

## 📝 Common Tasks

### Change Base URL
Edit `src/test/resources/config.properties`:
```properties
base.url=https://your-api-url.com
```

### Change Test Data
```java
// Generate random pet
Pet pet = TestDataGenerator.generatePet();

// Or create custom pet
Pet customPet = Pet.builder()
    .name("MyPet")
    .status("available")
    .build();
```

### Add Custom Validations
```java
// Status code validation
AssertionUtils.assertStatusCode(response, 200);

// Response time validation
AssertionUtils.assertResponseTime(response, 2000);

// JSON path validation
AssertionUtils.assertJsonPathValue(response, "name", "doggie");

// Content type validation
AssertionUtils.assertContentType(response, "application/json");
```

### Add Logging
```java
// In Extent Report
ExtentReportManager.logInfo("Starting test");
ExtentReportManager.logPass("Test passed");
ExtentReportManager.logFail("Test failed");

// In Log file
LoggerUtil.info("Test information");
LoggerUtil.error("Error occurred");
```

## 🚀 Run Tests in Different Ways

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

### Run tests by groups (if configured)
```bash
mvn test -Dgroups=smoke
```

### Run tests in parallel
```bash
mvn test -Dparallel=classes -DthreadCount=3
```

### Run with custom testng.xml
```bash
mvn test -DsuiteXmlFile=custom-testng.xml
```

## 🎨 IDE Setup

### IntelliJ IDEA
1. Open the project folder
2. File → Project Structure → Project SDK → Select Java 11+
3. Enable annotation processing:
   - Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Check "Enable annotation processing"
4. Right-click `testng.xml` → Run

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Right-click project → Maven → Update Project
3. Install TestNG plugin from Eclipse Marketplace
4. Right-click `testng.xml` → Run As → TestNG Suite

### VS Code
1. Install Extensions:
   - Extension Pack for Java
   - TestNG Runner
2. Open project folder
3. Run tests from Test Explorer

## 📊 Understanding Reports

### Extent Report Features
- **Dashboard**: Overview of test execution
- **Test Details**: Step-by-step test execution
- **Logs**: Info, Pass, Fail, Skip logs
- **Timeline**: Test execution timeline
- **System Info**: Environment details

### Log Levels
Configure in `src/test/resources/log4j2.xml`:
- TRACE: Very detailed
- DEBUG: Debug information
- INFO: General information (default)
- WARN: Warning messages
- ERROR: Error messages
- FATAL: Fatal errors

## 🔧 Troubleshooting

### Tests not running?
```bash
# Clean and rebuild
mvn clean install -DskipTests
```

### Compilation errors?
- Ensure Java 11+ is installed
- Check IDE annotation processing is enabled (for Lombok)
- Reload Maven project

### No reports generated?
- Check `test-output` folder exists
- Verify `@BeforeSuite` and `@AfterSuite` are executing
- Check file permissions

### Connection timeout?
- Check internet connection
- Verify API URL in `config.properties`
- Check firewall settings

## 📚 Next Steps

1. **Explore existing tests**: Check `src/test/java/com/petshop/tests/`
2. **Read README**: Detailed framework documentation
3. **Check examples**: Sample test data in `src/test/resources/testdata/`
4. **Customize**: Modify configurations in `config.properties`

## 💡 Pro Tips

1. **Use TestDataGenerator** for dynamic test data
2. **Log extensively** using ExtentReportManager
3. **Follow test naming conventions**: `test<Action><Scenario>`
4. **Use meaningful descriptions** in @Test annotation
5. **Keep tests independent** and idempotent
6. **Clean up test data** after execution

## 🎉 You're Ready!

You now have a fully functional REST Assured framework. Happy Testing! 🚀

For detailed documentation, check [README.md](README.md)

