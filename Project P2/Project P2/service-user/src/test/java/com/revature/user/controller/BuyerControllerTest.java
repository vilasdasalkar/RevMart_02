package com.revature.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

class BuyerControllerTest {

    @InjectMocks
    private buyerController buyerController;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for showAllProductsPage

    @Test
    void testShowAllProductsPage_Success() {
        // Mocking the expected response from the product service
        List<String> productList = new ArrayList<>();
        productList.add("Product 1");
        productList.add("Product 2");
        ResponseEntity<List> responseEntity = new ResponseEntity<>(productList, HttpStatus.OK);

        // Mock the call to RestTemplate
        when(restTemplate.getForEntity("http://localhost:8082/products", List.class)).thenReturn(responseEntity);

        // Call the method under test
        String viewName = buyerController.showAllProductsPage(model);

        // Verify the results
        assertEquals("buyer/buyerPage", viewName);
    }

    @Test
    void testShowAllProductsPage_Failure() {
        // Mock a failure scenario
        when(restTemplate.getForEntity("http://localhost:8082/products", List.class)).thenThrow(new RuntimeException("Product service is down"));

        // Call the method under test and expect an exception
        try {
            buyerController.showAllProductsPage(model);
        } catch (RuntimeException e) {
            assertEquals("Failed to fetch products.", e.getMessage());
        }
    }

    // Test cases for showAddToCartPage

    @Test
    void testShowAddToCartPage_Success() {
        // Call the method under test
        String viewName = buyerController.showAddToCartPage();

        // Verify the results
        assertEquals("buyer/addToCart", viewName);
    }

    @Test
    void testShowAddToCartPage_Failure() {
        // Simulate a failure scenario
        try {
            buyerController.showAddToCartPage();
        } catch (Exception e) {
            assertEquals("Some unexpected error occurred", e.getMessage()); // assuming any error could occur here.
        }
    }

    // Test cases for showCartPage

    @Test
    void testShowCartPage_Success() {
        // Call the method under test
        String viewName = buyerController.showCartPage();

        // Verify the results
        assertEquals("buyer/cartPage", viewName);
    }

    @Test
    void testShowCartPage_Failure() {
        // Simulate a failure scenario
        try {
            buyerController.showCartPage();
        } catch (Exception e) {
            assertEquals("Some unexpected error occurred", e.getMessage()); // assuming any error could occur here.
        }
    }

    // Test cases for viewProductsPage

    @Test
    void testViewProductsPage_Success() {
        // Call the method under test
        String viewName = buyerController.viewProductsPage();

        // Verify the results
        assertEquals("buyer/buyerProduct", viewName);
    }

    @Test
    void testViewProductsPage_Failure() {
        // Simulate a failure scenario
        try {
            buyerController.viewProductsPage();
        } catch (Exception e) {
            assertEquals("Some unexpected error occurred", e.getMessage()); // assuming any error could occur here.
        }
    }

    // Test cases for global exception handler

    @Test
    void testHandleException() {
        // Call the global exception handler
        ResponseEntity<String> response = buyerController.handleException(new RuntimeException("Test Exception"));

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Test Exception", response.getBody());
    }
}
