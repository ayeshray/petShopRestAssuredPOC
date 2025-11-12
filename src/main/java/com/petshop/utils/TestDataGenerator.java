package com.petshop.utils;

import com.github.javafaker.Faker;
import com.petshop.api.payloads.Order;
import com.petshop.api.payloads.Pet;
import com.petshop.api.payloads.User;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Test Data Generator using Java Faker
 * Follows Single Responsibility Principle - Handles only test data generation
 */
public class TestDataGenerator {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    private TestDataGenerator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Generate random Pet object
     * @return Pet object
     */
    public static Pet generatePet() {
        return Pet.builder()
                .id(generateRandomId())
                .name(faker.animal().name())
                .category(Pet.Category.builder()
                        .id(generateRandomId())
                        .name(faker.animal().genus())
                        .build())
                .photoUrls(Arrays.asList(
                        faker.internet().image(),
                        faker.internet().image()
                ))
                .tags(Arrays.asList(
                        Pet.Tag.builder()
                                .id(generateRandomId())
                                .name(faker.lorem().word())
                                .build()
                ))
                .status(getRandomPetStatus())
                .build();
    }

    /**
     * Generate Pet with specific name
     * @param name Pet name
     * @return Pet object
     */
    public static Pet generatePetWithName(String name) {
        Pet pet = generatePet();
        pet.setName(name);
        return pet;
    }

    /**
     * Generate Pet with specific status
     * @param status Pet status
     * @return Pet object
     */
    public static Pet generatePetWithStatus(String status) {
        Pet pet = generatePet();
        pet.setStatus(status);
        return pet;
    }

    /**
     * Generate random User object
     * @return User object
     */
    public static User generateUser() {
        return User.builder()
                .id(generateRandomId())
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password(8, 12))
                .phone(faker.phoneNumber().phoneNumber())
                .userStatus(random.nextInt(3))
                .build();
    }

    /**
     * Generate User with specific username
     * @param username Username
     * @return User object
     */
    public static User generateUserWithUsername(String username) {
        User user = generateUser();
        user.setUsername(username);
        return user;
    }

    /**
     * Generate random Order object
     * @return Order object
     */
    public static Order generateOrder() {
        return Order.builder()
                .id(generateRandomId())
                .petId(generateRandomId())
                .quantity(random.nextInt(10) + 1)
                .shipDate(getCurrentDateTime())
                .status(getRandomOrderStatus())
                .complete(random.nextBoolean())
                .build();
    }

    /**
     * Generate Order with specific pet ID
     * @param petId Pet ID
     * @return Order object
     */
    public static Order generateOrderWithPetId(Long petId) {
        Order order = generateOrder();
        order.setPetId(petId);
        return order;
    }

    /**
     * Generate random ID
     * @return Random ID
     */
    public static Long generateRandomId() {
        return (long) random.nextInt(100000) + 1;
    }

    /**
     * Get random pet status
     * @return Random pet status
     */
    public static String getRandomPetStatus() {
        String[] statuses = {"available", "pending", "sold"};
        return statuses[random.nextInt(statuses.length)];
    }

    /**
     * Get random order status
     * @return Random order status
     */
    public static String getRandomOrderStatus() {
        String[] statuses = {"placed", "approved", "delivered"};
        return statuses[random.nextInt(statuses.length)];
    }

    /**
     * Get current date time in ISO format
     * @return Current date time
     */
    public static String getCurrentDateTime() {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
    }

    /**
     * Generate list of users
     * @param count Number of users
     * @return List of users
     */
    public static List<User> generateUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(generateUser());
        }
        return users;
    }
}

