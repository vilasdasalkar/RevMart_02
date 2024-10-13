package com.revature.user.service;

import com.revature.user.model.User;
import com.revature.user.model.User.Role;
import com.revature.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Save a user
    public User saveUser(User user) {
        logger.info("Saving new user: {}", user.getEmail());
        
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt password
            User savedUser = userRepository.save(user);
            logger.info("User saved successfully with ID: {}", savedUser.getId());
            return savedUser;
        } catch (Exception e) {
            logger.error("Error while saving user: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to save user.");
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

    // Get all users
    public List<User> getAllUsers() {
        logger.info("Fetching all users");

        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Error while fetching all users", e);
            throw new RuntimeException("Failed to fetch users.");
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

    // Add role to user
    public void addRoleToUser(Long userId, Role role) {
        logger.info("Adding role {} to user with ID: {}", role, userId);

        try {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return new RuntimeException("User not found");
                });
            user.getRoles().add(role);
            userRepository.save(user);
            logger.info("Role {} added to user with ID: {}", role, userId);
        } catch (Exception e) {
            logger.error("Error while adding role {} to user with ID: {}", role, userId, e);
            throw new RuntimeException("Failed to add role to user.");
        }
    }
}
