package com.revature.user.controller;

import com.revature.user.model.User;
import com.revature.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Step 2: Add logging and exception handling for register
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        logger.info("Registering new user: {}", user.getUsername());

        try {
            User registeredUser = userService.register(user);
            logger.info("User registered successfully with ID: {}", registeredUser.getId());
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            logger.error("Error while registering user: {}", user, e);
            throw new RuntimeException("Failed to register user.");
        }
    }

    // Step 3: Add logging and exception handling for login
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        logger.info("User login attempt with username: {}", username);

        try {
            User loggedInUser = userService.login(username, password);
            logger.info("User logged in successfully with username: {}", username);
            return ResponseEntity.ok(loggedInUser);
        } catch (Exception e) {
            logger.error("Error during login for username: {}", username, e);
            throw new RuntimeException("Login failed.");
        }
    }

    // Step 4: Add logging and exception handling for getAllUsers
    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Fetching all users");

        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            logger.error("Error while fetching all users", e);
            throw new RuntimeException("Failed to fetch users.");
        }
    }

    // Step 5: Add logging and exception handling for getUserById
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);

        try {
            return userService.getUserById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        logger.warn("User not found with ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            logger.error("Error while fetching user with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch user.");
        }
    }

    // Step 6: Add logging and exception handling for updateUser
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        logger.info("Updating user with ID: {}", id);

        try {
            User updatedUser = userService.updateUser(id, userDetails);
            logger.info("User updated successfully with ID: {}", updatedUser.getId());
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            logger.error("Error while updating user with ID: {}", id, e);
            throw new RuntimeException("Failed to update user.");
        }
    }

    // Step 7: Add logging and exception handling for deleteUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);

        try {
            userService.deleteUser(id);
            logger.info("User deleted successfully with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error while deleting user with ID: {}", id, e);
            throw new RuntimeException("Failed to delete user.");
        }
    }

    // Step 8: Global exception handler for handling exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
