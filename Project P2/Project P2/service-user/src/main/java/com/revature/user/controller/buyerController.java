package com.revature.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class buyerController {

    // Step 1: Create a Logger instance for logging
    private static final Logger logger = LoggerFactory.getLogger(buyerController.class);

    // Step 2: Log method entry and exit points with exception handling
    @GetMapping("/buyerPage")
    public String showAllProductsPage(Model model) {
        logger.info("Entering showAllProductsPage()");

        // URL to fetch products from the product service
        String productUrl = "http://localhost:8082/products";

        try {
            // Step 3: Log external service call and results
            logger.info("Fetching products from URL: {}", productUrl);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List> response = restTemplate.getForEntity(productUrl, List.class);

            // Step 4: Log success and add data to model
            logger.info("Successfully fetched products");
            model.addAttribute("products", response.getBody());

        } catch (Exception e) {
            // Step 5: Log any exception
            logger.error("Error while fetching products", e);
            throw new RuntimeException("Failed to fetch products.");
        }

        logger.info("Exiting showAllProductsPage()");
        return "buyer/buyerPage"; // Corresponds to allProduct.jsp
    }

    @GetMapping("/addToCart")
    public String showAddToCartPage() {
        logger.info("Navigating to add to cart page");
        return "buyer/addToCart";  // Corresponds to the new addToCart.jsp
    }

    @GetMapping("/cartPage")
    public String showCartPage() {
        logger.info("Navigating to cart page");
        return "buyer/cartPage";  // This corresponds to cartPage.jsp
    }

    @GetMapping("/buyerAllProduct")
    public String viewProductsPage() {
        logger.info("Navigating to all products page for buyer");
        return "buyer/buyerProduct";  // This returns the JSP page
    }

    // Step 6: Global exception handler for the controller
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
