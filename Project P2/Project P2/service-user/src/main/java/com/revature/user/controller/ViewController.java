package com.revature.user.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.revature.user.model.User;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class ViewController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        logger.info("Navigating to the home page.");
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        logger.info("Navigating to the login page.");
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        logger.info("Navigating to the registration page.");
        return "registration";
    }

    // Step 2: Add logging and exception handling for the login process
    @PostMapping("/doLogin")
    public ModelAndView doLogin(@RequestParam String email, @RequestParam String password) {
        logger.info("Attempting login for email: {}", email);

        try {
            String apiUrl = "http://localhost:8080/api/users"; // The API URL for fetching users
            User[] users = restTemplate.getForObject(apiUrl, User[].class);

            if (users != null) {
                List<User> userList = Arrays.asList(users);
                for (User user : userList) {
                    if (user.getEmail().equals(email) && passwordEncoder.matches(password, user.getPassword())) {
                        User.Role role = user.getRoles().iterator().next();
                        logger.info("Login successful for email: {}, Role: {}", email, role);

                        if (role.equals(User.Role.ADMIN)) {
                            return new ModelAndView("redirect:/adminPage");
                        } else if (role.equals(User.Role.SELLER)) {
                            return new ModelAndView("redirect:/sellerPage");
                        } else if (role.equals(User.Role.BUYER)) {
                            return new ModelAndView("redirect:/buyerPage");
                        }
                    }
                }
            }

            logger.warn("Login failed for email: {}", email);
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Invalid email or password.");
            return modelAndView;
        } catch (Exception e) {
            logger.error("Error during login for email: {}", email, e);
            throw new RuntimeException("Failed to process login request.");
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        logger.info("Logging out and invalidating session.");
        try {
            session.invalidate();
            return new ModelAndView("redirect:http://localhost:8080/");
        } catch (Exception e) {
            logger.error("Error during logout.", e);
            throw new RuntimeException("Failed to process logout request.");
        }
    }

    @GetMapping("/adminPage")
    public String adminPage() {
        logger.info("Navigating to the admin page.");
        return "adminPage";
    }

    @GetMapping("/sellerPage")
    public String sellerPage() {
        logger.info("Navigating to the seller page.");
        return "sellerPage";
    }

    // Step 3: Add a global exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
