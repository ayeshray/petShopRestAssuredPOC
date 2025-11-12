# Contributing to PetShop REST Assured Framework

Thank you for considering contributing to this project! This document provides guidelines and best practices for contributing.

## 🤝 Code of Conduct

- Be respectful and constructive
- Focus on what is best for the community
- Show empathy towards other contributors

## 🎯 How to Contribute

### Reporting Bugs

1. Check if the bug has already been reported
2. Use the bug report template
3. Include:
   - Clear description
   - Steps to reproduce
   - Expected vs actual behavior
   - Screenshots if applicable
   - Environment details

### Suggesting Enhancements

1. Check if the enhancement has been suggested
2. Provide clear use cases
3. Explain why it would be useful

### Pull Requests

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Make your changes
4. Write/update tests
5. Ensure all tests pass
6. Update documentation
7. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
8. Push to the branch (`git push origin feature/AmazingFeature`)
9. Open a Pull Request

## 📋 Development Guidelines

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Keep methods small and focused (< 20 lines ideally)
- Add JavaDoc comments for public methods
- Follow SOLID principles

### Example Code Style

```java
/**
 * Creates a new pet in the system
 * @param pet Pet object to create
 * @return Response from the API
 */
public Response createPet(Pet pet) {
    ExtentReportManager.logInfo("Creating pet: " + pet.getName());
    
    Response response = given()
        .spec(RequestSpecifications.getBasicRequestSpec())
        .body(pet)
        .when()
        .post(basePath);
    
    return response;
}
```

### Testing Guidelines

1. Write tests for new features
2. Ensure all existing tests pass
3. Follow AAA pattern (Arrange, Act, Assert)
4. Use descriptive test names
5. Add test descriptions

```java
@Test(priority = 1, description = "Verify pet creation with valid data")
public void testCreatePetWithValidData() {
    // Arrange
    Pet pet = TestDataGenerator.generatePet();
    
    // Act
    Response response = petEndpoint.create(pet);
    
    // Assert
    AssertionUtils.assertStatusCode(response, 200);
}
```

### Commit Message Guidelines

Format: `[type]: [short description]`

Types:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

Examples:
```
feat: Add support for XML response validation
fix: Correct timeout configuration issue
docs: Update README with new examples
test: Add tests for Store API endpoint
```

### Branch Naming

- `feature/feature-name` - New features
- `bugfix/bug-description` - Bug fixes
- `hotfix/critical-fix` - Critical fixes
- `refactor/refactor-description` - Refactoring

## 🔍 Code Review Process

1. At least one approval required
2. All tests must pass
3. Code must follow style guidelines
4. Documentation must be updated
5. No merge conflicts

## 📦 Project Structure

When adding new features, maintain the existing structure:

```
src/main/java/com/petshop/
├── api/
│   ├── endpoints/      # Add new endpoint classes here
│   ├── payloads/       # Add new POJO classes here
│   └── specifications/ # Add new specifications here
├── config/            # Configuration classes
└── utils/             # Utility classes

src/test/java/com/petshop/
├── base/              # Base test classes
├── listeners/         # TestNG listeners
└── tests/             # Test classes
```

## 🧪 Testing Checklist

Before submitting a PR, ensure:

- [ ] All new code has tests
- [ ] All tests pass locally
- [ ] Code follows style guidelines
- [ ] Documentation is updated
- [ ] Commit messages are clear
- [ ] No debug code or comments
- [ ] No hardcoded values

## 📚 Resources

- [REST Assured Documentation](https://rest-assured.io/)
- [TestNG Documentation](https://testng.org/doc/)
- [Clean Code Principles](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)

## 🙋 Questions?

Feel free to open an issue for any questions or clarifications.

## 🎉 Recognition

Contributors will be acknowledged in the project documentation.

Thank you for contributing! 🚀

