package com.revature.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);

    // Step 2: Add logging and exception handling for getWishlistPage
    @GetMapping()
    public String getWishlistPage() {
        logger.info("Fetching the wishlist page.");
        
        try {
            // Business logic can be added here if necessary
            return "wishlist";  // This should map to src/main/webapp/WEB-INF/views/wishlist.jsp
        } catch (Exception e) {
            logger.error("Error occurred while loading the wishlist page", e);
            throw new RuntimeException("Failed to load the wishlist page.");
        }
    }

    // Step 3: Add a global exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
