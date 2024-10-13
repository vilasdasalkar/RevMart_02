package com.revature.user.controller;

import com.revature.user.model.OrderResponse;
import com.revature.user.model.ProductResponse;
import com.revature.user.service.OrderService;
import com.revature.user.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private OrderService orderService;

    @Mock
    private Model model;

    // Test for showHomePage (Success)
    @Test
    public void testShowHomePage_Success() {
        // Mock the ProductResponse list
        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(),
                new ProductResponse()
        );

        // Mock the ProductService call
        when(productService.getAllProducts()).thenReturn(products);

        // Call the controller method
        String viewName = productController.showHomePage(model);

        // Verify the model attribute and service call
        verify(model, times(1)).addAttribute("products", products);
        verify(productService, times(1)).getAllProducts();

        // Assert the correct view name is returned
        assertEquals("home", viewName);
    }

    // Test for showHomePage (Failure)
    @Test
    public void testShowHomePage_Failure() {
        // Simulate an exception in the ProductService
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Failed to fetch products"));

        // Call the controller method and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productController.showHomePage(model);
        });

        // Assert that the exception message is correct
        assertEquals("Failed to load home page products.", exception.getMessage());
    }

    // Test for getAllProducts (Success)
    @Test
    public void testGetAllProducts_Success() {
        // Mock the ProductResponse list
        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(),
                new ProductResponse()
        );

        // Mock the ProductService call
        when(productService.getAllProducts()).thenReturn(products);

        // Call the controller method
        String viewName = productController.getAllProducts(model);

        // Verify the model attribute and service call
        verify(model, times(1)).addAttribute("products", products);
        verify(productService, times(1)).getAllProducts();

        // Assert the correct view name is returned
        assertEquals("product", viewName);
    }

    // Test for getAllProducts (Failure)
    @Test
    public void testGetAllProducts_Failure() {
        // Simulate an exception in the ProductService
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Failed to fetch products"));

        // Call the controller method and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productController.getAllProducts(model);
        });

        // Assert that the exception message is correct
        assertEquals("Failed to load products.", exception.getMessage());
    }

    // Test for getAllOrders (Success)
    @Test
    public void testGetAllOrders_Success() {
        // Mock the OrderResponse list
        List<OrderResponse> orders = Arrays.asList(
                new OrderResponse(),
                new OrderResponse()
        );

        // Mock the OrderService call
        when(orderService.getAllOrders()).thenReturn(orders);

        // Call the controller method
        String viewName = productController.getAllOrders(model);

        // Verify the model attribute and service call
        verify(model, times(1)).addAttribute("orders", orders);
        verify(orderService, times(1)).getAllOrders();

        // Assert the correct view name is returned
        assertEquals("orders", viewName);
    }

    // Test for getAllOrders (Failure)
    @Test
    public void testGetAllOrders_Failure() {
        // Simulate an exception in the OrderService
        when(orderService.getAllOrders()).thenThrow(new RuntimeException("Failed to fetch orders"));

        // Call the controller method and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productController.getAllOrders(model);
        });

        // Assert that the exception message is correct
        assertEquals("Failed to load orders.", exception.getMessage());
    }

    // Test for handleException (Success)
    @Test
    public void testHandleException_Success() {
        // Simulate an exception
        Exception exception = new Exception("Some error occurred");

        // Call the exception handler method
        ResponseEntity<String> response = productController.handleException(exception);

        // Assert the response status and message
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Some error occurred", response.getBody());
    }
}
