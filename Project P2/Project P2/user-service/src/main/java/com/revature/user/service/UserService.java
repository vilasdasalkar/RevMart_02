package com.revature.user.service;

import com.revature.user.model.User;
import com.revature.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public User register(User user) {
        logger.info("Registering new user: {}", user.getUsername());

        try {
            Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
            if (existingUser.isPresent()) {
                logger.warn("Username is already taken: {}", user.getUsername());
                throw new RuntimeException("Username is already taken.");
            }

            User savedUser = userRepository.save(user);
            logger.info("User registered successfully with ID: {}", savedUser.getId());
            return savedUser;
        } catch (Exception e) {
            logger.error("Error while registering user: {}", user.getUsername(), e);
            throw new RuntimeException("Failed to register user.");
        }
    }

    // Login
    public User login(String username, String password) {
        logger.info("Attempting login for username: {}", username);

        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent() && user.get().getPassword().equals(password)) {
                logger.info("User logged in successfully: {}", username);
                return user.get();  // Successfully logged in
            } else {
                logger.warn("Invalid username or password for username: {}", username);
                throw new RuntimeException("Invalid username or password.");
            }
        } catch (Exception e) {
            logger.error("Error during login for username: {}", username, e);
            throw new RuntimeException("Failed to login.");
        }
    }

    // Get all users
    public List<User> getAllUsers() {
        logger.info("Fetching all users");

        try {
            List<User> users = userRepository.findAll();
            logger.info("Fetched {} users", users.size());
            return users;
        } catch (Exception e) {
            logger.error("Error while fetching users", e);
            throw new RuntimeException("Failed to fetch users.");
        }
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);

        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error while fetching user with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch user.");
        }
    }

    // Update user
    public User updateUser(Long id, User userDetails) {
        logger.info("Updating user with ID: {}", id);

        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("User not found with ID: {}", id);
                        return new RuntimeException("User not found.");
                    });

            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());

            User updatedUser = userRepository.save(user);
            logger.info("User updated successfully with ID: {}", id);
            return updatedUser;
        } catch (Exception e) {
            logger.error("Error while updating user with ID: {}", id, e);
            throw new RuntimeException("Failed to update user.");
        }
    }

    // Delete user
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);

        try {
            userRepository.deleteById(id);
            logger.info("User deleted successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error while deleting user with ID: {}", id, e);
            throw new RuntimeException("Failed to delete user.");
        }
    }
}
