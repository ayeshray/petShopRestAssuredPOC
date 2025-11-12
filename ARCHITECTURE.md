# Framework Architecture Documentation

## 🏗️ Architecture Overview

This document provides a detailed view of the framework architecture, design patterns, and component interactions.

---

## 📐 Layered Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        TEST LAYER                               │
│  ┌───────────────┐  ┌───────────────┐  ┌───────────────┐      │
│  │  PetApiTest   │  │  UserApiTest  │  │ StoreApiTest  │      │
│  └───────────────┘  └───────────────┘  └───────────────┘      │
│         │                   │                   │               │
└─────────┼───────────────────┼───────────────────┼───────────────┘
          │                   │                   │
          ▼                   ▼                   ▼
┌─────────────────────────────────────────────────────────────────┐
│                     API ENDPOINT LAYER                          │
│  ┌───────────────┐  ┌───────────────┐  ┌───────────────┐      │
│  │  PetEndpoint  │  │  UserEndpoint │  │ StoreEndpoint │      │
│  └───────────────┘  └───────────────┘  └───────────────┘      │
│         │                   │                   │               │
│         └───────────────────┴───────────────────┘               │
│                             │                                   │
│                   ┌─────────▼──────────┐                       │
│                   │  IApiEndpoint      │                       │
│                   │  (Interface)       │                       │
│                   └────────────────────┘                       │
└─────────────────────────────────────────────────────────────────┘
          │                   │                   │
          ▼                   ▼                   ▼
┌─────────────────────────────────────────────────────────────────┐
│                    PAYLOAD/POJO LAYER                           │
│  ┌───────────────┐  ┌───────────────┐  ┌───────────────┐      │
│  │   Pet.java    │  │   User.java   │  │  Order.java   │      │
│  └───────────────┘  └───────────────┘  └───────────────┘      │
│                                                                 │
│  ┌───────────────────────────────────────────────────┐         │
│  │  ApiResponse.java (Generic Response Handler)      │         │
│  └───────────────────────────────────────────────────┘         │
└─────────────────────────────────────────────────────────────────┘
          │                   │
          ▼                   ▼
┌─────────────────────────────────────────────────────────────────┐
│                  SPECIFICATION LAYER                            │
│  ┌──────────────────────────┐  ┌──────────────────────────┐    │
│  │ RequestSpecifications    │  │ ResponseSpecifications   │    │
│  │  - Basic Request Spec    │  │  - Success Response Spec │    │
│  │  - Auth Request Spec     │  │  - Created Response Spec │    │
│  │  - Multipart Spec        │  │  - Error Response Spec   │    │
│  └──────────────────────────┘  └──────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘
          │
          ▼
┌─────────────────────────────────────────────────────────────────┐
│                 SUPPORT & UTILITY LAYER                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐         │
│  │    Config    │  │   Utilities  │  │   Listeners  │         │
│  │              │  │              │  │              │         │
│  │ ConfigMgr    │  │ TestDataGen  │  │ TestListener │         │
│  │ ConfigFctry  │  │ ExtentReport │  │ RetryAnalzr  │         │
│  │              │  │ JsonUtils    │  │              │         │
│  │              │  │ AssertUtils  │  │              │         │
│  │              │  │ LoggerUtil   │  │              │         │
│  └──────────────┘  └──────────────┘  └──────────────┘         │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🎯 Design Patterns Implementation

### 1. Page Object Model (POM) - Adapted for APIs

**API Object Model Pattern**

```
Test Classes
    ↓ (uses)
Endpoint Classes
    ↓ (returns)
Response Objects
```

**Benefits:**
- Separation of test logic from API calls
- Reusable API methods
- Easy maintenance
- Single point of change

**Example:**
```java
// Test Layer
PetApiTest → petEndpoint.create(pet)
                    ↓
            // Endpoint Layer
            PetEndpoint.create(pet)
                    ↓
            // Returns Response
            Response
```

---

### 2. Singleton Pattern

**Used in:** `ConfigFactory`, `ExtentReportManager`

```
┌─────────────────────┐
│   ConfigFactory     │
│                     │
│  - instance: null   │
│                     │
│  + getConfig()      │ ──┐
└─────────────────────┘   │
                          │ Returns single instance
                          ▼
                ┌──────────────────────┐
                │  ConfigManager       │
                │  (Single Instance)   │
                └──────────────────────┘
```

**Benefits:**
- Single instance throughout application
- Global point of access
- Lazy initialization
- Thread-safe

---

### 3. Factory Pattern

**Used in:** `ConfigFactory`, Request/Response Specifications

```
Client Code
    ↓
┌─────────────────────────────────┐
│  RequestSpecifications          │
│                                 │
│  + getBasicRequestSpec()        │ ───→ RequestSpecification
│  + getAuthRequestSpec()         │ ───→ RequestSpecification
│  + getMultipartRequestSpec()    │ ───→ RequestSpecification
└─────────────────────────────────┘
```

**Benefits:**
- Centralized object creation
- Easy to add new types
- Consistent configuration
- Reduced duplication

---

### 4. Builder Pattern

**Used in:** POJO classes with Lombok

```java
Pet.builder()
    .id(123L)
    .name("Rocky")
    .status("available")
    .category(Category.builder()
        .id(1L)
        .name("Dogs")
        .build())
    .build();
```

**Benefits:**
- Fluent API
- Immutable objects option
- Readable code
- Optional parameters

---

### 5. Observer Pattern

**Used in:** TestNG Listeners

```
┌─────────────────┐          ┌─────────────────┐
│   TestNG        │          │  TestListener   │
│   Runner        │ ────────→│                 │
│                 │  Notify  │  - onStart()    │
│                 │          │  - onFinish()   │
│                 │          │  - onSuccess()  │
│                 │          │  - onFailure()  │
└─────────────────┘          └─────────────────┘
                                     │
                                     ▼
                          ┌─────────────────────┐
                          │  ExtentReport       │
                          │  Logger             │
                          └─────────────────────┘
```

**Benefits:**
- Automatic reporting
- Centralized logging
- Event-driven actions
- Loose coupling

---

## 🔄 Request/Response Flow

### Complete API Call Flow

```
1. Test Execution
   └─→ @Test method in PetApiTest

2. Test Data Generation
   └─→ TestDataGenerator.generatePet()
       └─→ Returns Pet object

3. API Call
   └─→ petEndpoint.create(pet)
       └─→ RequestSpecifications.getBasicRequestSpec()
           └─→ Configures: BaseURI, Headers, Content-Type
       └─→ REST Assured request
           └─→ Serializes Pet to JSON (Jackson)
           └─→ Sends HTTP POST request

4. Response Handling
   └─→ Receives HTTP Response
       └─→ Logs request/response (Filters)
       └─→ Returns Response object

5. Validation
   └─→ AssertionUtils.assertStatusCode()
   └─→ JsonUtils.fromResponse()
       └─→ Deserializes JSON to Pet object
   └─→ Assert on Pet object properties

6. Reporting
   └─→ ExtentReportManager.logPass()
   └─→ LoggerUtil.info()
   └─→ TestListener.onSuccess()
```

---

## 🎨 SOLID Principles Implementation

### 1. Single Responsibility Principle (SRP)

**Each class has one reason to change**

```
┌─────────────────────────────────────────────────────────────┐
│  PetEndpoint        → Only handles Pet API calls            │
│  TestDataGenerator  → Only generates test data              │
│  ExtentReportMgr    → Only manages reports                  │
│  ConfigFactory      → Only manages configuration            │
│  AssertionUtils     → Only handles assertions               │
└─────────────────────────────────────────────────────────────┘
```

### 2. Open/Closed Principle (OCP)

**Open for extension, closed for modification**

```
        IApiEndpoint (Interface)
               ▲
               │ Implements
               │
    ┌──────────┼──────────┐
    │          │          │
PetEndpoint UserEndpoint StoreEndpoint
```

New endpoints can be added by implementing `IApiEndpoint` without modifying existing code.

### 3. Liskov Substitution Principle (LSP)

**Derived classes should be substitutable for base classes**

```java
IApiEndpoint<Pet> endpoint = new PetEndpoint();
Response response = endpoint.create(pet); // Works seamlessly
```

### 4. Interface Segregation Principle (ISP)

**Clients shouldn't depend on interfaces they don't use**

```
┌──────────────────┐
│  IApiEndpoint    │ ← Small, focused interface
│  - create()      │
│  - getById()     │
│  - update()      │
│  - delete()      │
└──────────────────┘

Not:
┌──────────────────────┐
│  IAllOperations      │ ← Large, bloated interface
│  - create()          │
│  - getById()         │
│  - update()          │
│  - delete()          │
│  - search()          │
│  - filter()          │
│  - export()          │
│  - import()          │
│  ... (50 methods)   │
└──────────────────────┘
```

### 5. Dependency Inversion Principle (DIP)

**Depend on abstractions, not concretions**

```
PetApiTest
    ↓ depends on
PetEndpoint (implements IApiEndpoint)
    ↓ depends on
RequestSpecifications (static factory methods)
    ↓ depends on
ConfigManager (interface)
```

---

## 📊 Component Interaction Diagram

### Configuration Loading

```
Application Start
    ↓
ConfigFactory.getConfig()
    ↓
Loads ConfigManager (Owner Library)
    ↓
Reads config.properties
    ↓
Returns ConfigManager instance
    ↓
Used throughout application
```

### Test Execution Flow

```
@BeforeSuite
    ↓
ExtentReportManager.initReports()
RestAssured.configure()
    ↓
@BeforeClass (Per Test Class)
    ↓
Initialize Endpoints
    ↓
@Test (Per Test Method)
    ↓
TestListener.onTestStart()
    ↓
Generate Test Data
    ↓
Make API Call
    ↓
Validate Response
    ↓
Log Results
    ↓
TestListener.onTestSuccess/Failure()
    ↓
@AfterSuite
    ↓
ExtentReportManager.flushReports()
```

---

## 🔐 Configuration Management

### Configuration Architecture

```
┌──────────────────┐
│ config.properties│
└────────┬─────────┘
         │
         ▼
┌────────────────────┐
│  Owner Library     │
└────────┬───────────┘
         │
         ▼
┌────────────────────┐
│  ConfigManager     │ (Interface with @Key annotations)
└────────┬───────────┘
         │
         ▼
┌────────────────────┐
│  ConfigFactory     │ (Singleton access point)
└────────┬───────────┘
         │
         ▼
Used by all components
```

**Benefits:**
- Type-safe configuration
- Centralized management
- Easy to change
- Default values support

---

## 🧩 Utility Components

### Utilities Dependency Map

```
                    Test Classes
                         │
         ┌───────────────┼───────────────┐
         ▼               ▼               ▼
   AssertionUtils  ExtentReportMgr  TestDataGen
         │               │               │
         └───────────────┼───────────────┘
                         │
                    LoggerUtil
                         │
                         ▼
                    Log4j2 Logger
```

---

## 🎪 Reporting Architecture

### Reporting Flow

```
Test Execution
    ↓
TestListener.onTestStart()
    ↓
ExtentReportManager.createTest()
    ↓
Test Steps Execute
    ↓
ExtentReportManager.logInfo/Pass/Fail()
    ↓
TestListener.onTestSuccess/Failure()
    ↓
ExtentReportManager.logPass/Fail()
    ↓
@AfterSuite
    ↓
ExtentReportManager.flushReports()
    ↓
HTML Report Generated
```

---

## 📈 Scalability Features

### How to Extend the Framework

#### 1. Add New API Endpoint

```java
public class NewEndpoint implements IApiEndpoint<NewModel> {
    // Implement CRUD operations
}
```

#### 2. Add New Payload/POJO

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewModel {
    // Add fields
}
```

#### 3. Add New Test Class

```java
public class NewApiTest extends BaseTest {
    // Add test methods
}
```

#### 4. Add to TestNG Suite

```xml
<test name="New API Tests">
    <classes>
        <class name="com.petshop.tests.NewApiTest"/>
    </classes>
</test>
```

---

## 🔄 Data Flow Diagram

```
Test Data
    ↓
Test Class
    ↓
Endpoint Class
    ↓
Request Specification
    ↓
REST Assured
    ↓
HTTP Request → API Server
    ↓
HTTP Response ← API Server
    ↓
Response Object
    ↓
Assertions & Validations
    ↓
Reports & Logs
```

---

## 🎯 Best Practices Implemented

### Code Organization
✅ Package by feature  
✅ Consistent naming  
✅ Proper encapsulation  
✅ Clear responsibilities  

### Error Handling
✅ Try-catch where needed  
✅ Meaningful error messages  
✅ Proper exception propagation  
✅ Logging at appropriate levels  

### Testing
✅ Independent tests  
✅ Proper test data management  
✅ Cleanup after tests  
✅ Negative testing  

### Documentation
✅ JavaDoc comments  
✅ README files  
✅ Inline comments  
✅ Architecture documentation  

---

## 🚀 Performance Considerations

### Parallel Execution

```xml
<suite name="Suite" parallel="classes" thread-count="3">
```

**Benefits:**
- Faster execution
- Better resource utilization
- Independent test execution

### Request/Response Logging

- Conditional logging based on config
- Log levels configurable
- File rotation for large logs

### Configuration Caching

- Singleton pattern for config
- Loaded once, used many times
- Memory efficient

---

## 📚 Reference Documentation

### Key Classes to Understand

1. **BaseTest.java** - Starting point for all tests
2. **IApiEndpoint.java** - Contract for all endpoints
3. **RequestSpecifications.java** - Request builders
4. **ExtentReportManager.java** - Reporting hub
5. **ConfigFactory.java** - Configuration access point

### Reading Order for New Developers

1. Read README.md
2. Study BaseTest.java
3. Review PetEndpoint.java
4. Examine PetApiTest.java
5. Explore utility classes

---

**This architecture provides a solid foundation for scalable, maintainable API test automation.**

---

*Last Updated: 2024*  
*Version: 1.0.0*

