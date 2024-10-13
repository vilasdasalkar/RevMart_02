package com.revature.user.controller;

import com.revature.user.model.User;
import com.revature.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Step 2: Add logging and exception handling for getAllUsers
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Fetching all users");
        try {
            List<User> users = userService.getAllUsers();
            logger.info("Successfully fetched users: {}", users.size());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching users", e);
            throw new RuntimeException("Failed to fetch users");
        }
    }

    // Step 3: Add logging and exception handling for getUserById
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Fetching user by id: {}", id);
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                logger.info("User found: {}", user.get());
                return ResponseEntity.ok(user.get());
            } else {
                logger.warn("User not found with id: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error fetching user by id: {}", id, e);
            throw new RuntimeException("Failed to fetch user by id");
        }
    }

    // Step 4: Add logging and exception handling for createUser
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Creating new user: {}", user);
        try {
            User savedUser = userService.saveUser(user);
            logger.info("User created successfully: {}", savedUser);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            logger.error("Error creating user: {}", user, e);
            throw new RuntimeException("Failed to create user");
        }
    }

    // Step 5: Add logging and exception handling for deleteUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user by id: {}", id);
        try {
            userService.deleteUser(id);
            logger.info("User deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting user by id: {}", id, e);
            throw new RuntimeException("Failed to delete user");
        }
    }

    // Step 6: Add logging and exception handling for addRoleToUser
    @PutMapping("/{id}/role")
    public ResponseEntity<Void> addRoleToUser(@PathVariable Long id, @RequestParam User.Role role) {
        logger.info("Adding role to user: {} with role: {}", id, role);
        try {
            userService.addRoleToUser(id, role);
            logger.info("Role added successfully to user: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error adding role to user: {} with role: {}", id, role, e);
            throw new RuntimeException("Failed to add role to user");
        }
    }

    // Step 7: Global exception handler for handling exceptions across all methods
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(500).body("An error occurred: " + ex.getMessage());
    }
}
