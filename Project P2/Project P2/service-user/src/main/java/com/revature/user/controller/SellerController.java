package com.revature.user.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;

@Controller
public class SellerController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(SellerController.class);

    // Mapping for Add Product
    @PostMapping("/postProduct")
    public ResponseEntity<String> postProduct(@RequestBody String jsonData) {
        logger.info("Entering postProduct()");
        String productUrl = "http://localhost:8087/products";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(productUrl, jsonData, String.class);
            logger.info("Product posted successfully.");
            return response;
        } catch (Exception e) {
            logger.error("Error posting product", e);
            throw new RuntimeException("Failed to post product.");
        }
    }

    @GetMapping("/addProduct")
    public String showAddProductPage() {
        logger.info("Navigating to addProduct.jsp");
        return "addProduct";
    }

    // Mapping for All Products Page
    @GetMapping("/allProduct")
    public String showAllProductsPage(Model model) {
        logger.info("Entering showAllProductsPage()");
        String productUrl = "http://localhost:8087/products";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List> response = restTemplate.getForEntity(productUrl, List.class);
            model.addAttribute("products", response.getBody());
            logger.info("Products fetched successfully.");
        } catch (Exception e) {
            logger.error("Error fetching products", e);
            throw new RuntimeException("Failed to fetch products.");
        }
        return "allProduct";
    }

    // Mapping for View Orders
    @GetMapping("/viewOrder")
    public String showViewOrdersPage() {
        logger.info("Navigating to viewOrder.jsp");
        return "viewOrder";
    }

    @PostMapping("/postCategory")
    public ResponseEntity<String> postCategory(@RequestBody String jsonData) {
        logger.info("Entering postCategory()");
        String categoryUrl = "http://localhost:8087/categories";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(categoryUrl, jsonData, String.class);
            logger.info("Category posted successfully.");
            return response;
        } catch (Exception e) {
            logger.error("Error posting category", e);
            throw new RuntimeException("Failed to post category.");
        }
    }

    @GetMapping("/addCategory")
    public String showAddCategoryPage() {
        logger.info("Navigating to addCategory.jsp");
        return "addCategory";
    }

    // Mapping for Edit Products
    @GetMapping("/editProduct")
    public String showEditProductsPage() {
        logger.info("Navigating to editProduct.jsp");
        return "editProduct";
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProductPage(@PathVariable Long id, Model model) {
        logger.info("Fetching product with ID: {}", id);
        String productUrl = "http://localhost:8087/products/" + id;

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.getForEntity(productUrl, Map.class);
            model.addAttribute("product", response.getBody());
            logger.info("Product fetched successfully for editing.");
        } catch (Exception e) {
            logger.error("Error fetching product with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch product for editing.");
        }

        return "editProduct";
    }

    @PostMapping("/updateProduct")
    public ResponseEntity<String> updateProduct(@RequestBody String jsonData) {
        logger.info("Updating product with data: {}", jsonData);
        String productUrl = "http://localhost:8087/products";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(productUrl, HttpMethod.PUT, 
                new HttpEntity<>(jsonData, new HttpHeaders()), String.class);
            logger.info("Product updated successfully.");
            return response;
        } catch (Exception e) {
            logger.error("Error updating product", e);
            throw new RuntimeException("Failed to update product.");
        }
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        String productUrl = "http://localhost:8087/products/" + id;

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(productUrl);
            logger.info("Product deleted successfully.");
        } catch (Exception e) {
            logger.error("Error deleting product with ID: {}", id, e);
            throw new RuntimeException("Failed to delete product.");
        }

        return "redirect:/allProduct";
    }

    // Step 4: Global exception handler for handling exceptions across all methods
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
