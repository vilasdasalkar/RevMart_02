package com.revature.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.revature.user.model.PaymentDetails;
import com.revature.user.service.PaymentService;

class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for processPayment

    @Test
    void testProcessPayment_Success() {
        // Mocking the expected payment status
        PaymentDetails paymentDetails = new PaymentDetails("100", "1234567890123456", "12/25", "123");
        when(paymentService.processPayment(paymentDetails)).thenReturn("Payment Successful");

        // Call the method under test
        String viewName = paymentController.processPayment("100", "1234567890123456", "12/25", "123", model);

        // Verify the results
        assertEquals("buyer/paymentResult", viewName);
    }

    @Test
    void testProcessPayment_MissingDetails() {
        // Call the method under test with missing details
        String viewName = paymentController.processPayment("", "1234567890123456", "12/25", "123", model);

        // Verify the results
        assertEquals("buyer/paymentForm", viewName);
    }

    @Test
    void testProcessPayment_Failure() {
        // Mock a failure scenario
        PaymentDetails paymentDetails = new PaymentDetails("100", "1234567890123456", "12/25", "123");
        doThrow(new RuntimeException("Payment failed")).when(paymentService).processPayment(paymentDetails);

        // Call the method under test and expect an exception
        try {
            paymentController.processPayment("100", "1234567890123456", "12/25", "123", model);
        } catch (RuntimeException e) {
            assertEquals("Failed to process payment.", e.getMessage());
        }
    }

    // Test cases for viewPaymentPage

    @Test
    void testViewPaymentPage_Success() {
        // Call the method under test
        String viewName = paymentController.viewPaymentPage(250.00, model);

        // Verify the results
        assertEquals("buyer/paymentForm", viewName);
    }

    @Test
    void testViewPaymentPage_Failure() {
        // Mock a failure scenario
        doThrow(new RuntimeException("Failed to load payment form")).when(model).addAttribute("totalPrice", 250.00);

        // Call the method under test and expect an exception
        try {
            paymentController.viewPaymentPage(250.00, model);
        } catch (RuntimeException e) {
            assertEquals("Failed to load payment form.", e.getMessage());
        }
    }

    // Test cases for successPayment

    @Test
    void testSuccessPayment_Success() {
        // Call the method under test
        String viewName = paymentController.successPayment();

        // Verify the results
        assertEquals("buyer/successPayment", viewName);
    }

    // Test cases for global exception handler

    @Test
    void testHandleException() {
        // Call the global exception handler
        ResponseEntity<String> response = paymentController.handleException(new RuntimeException("Test Exception"));

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Test Exception", response.getBody());
    }
}
