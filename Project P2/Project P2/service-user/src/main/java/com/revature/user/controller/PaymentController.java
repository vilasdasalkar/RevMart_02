package com.revature.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.revature.user.model.PaymentDetails;
import com.revature.user.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.InputMismatchException;

@Controller
public class PaymentController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    // Step 2: Add logging and exception handling for processPayment
    @PostMapping("/processPayment")
    public String processPayment(
            @RequestParam("amount") String amount,
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam("cvv") String cvv,
            Model model) {

        logger.info("Processing payment for amount: {}", amount);

        try {
            // Validate payment data (Example: basic input checks)
            if (amount.isEmpty() || cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                throw new InputMismatchException("Missing required payment details");
            }

            // Create a PaymentDetails object to pass to the service
            PaymentDetails paymentDetails = new PaymentDetails(amount, cardNumber, expiryDate, cvv);
            logger.debug("PaymentDetails: {}", paymentDetails);

            // Process the payment using the PaymentService
            String paymentStatus = paymentService.processPayment(paymentDetails);
            logger.info("Payment processed with status: {}", paymentStatus);

            // Add payment status to the model to display on the JSP page
            model.addAttribute("paymentStatus", paymentStatus);

            return "buyer/paymentResult";  // Redirect to result JSP page
        } catch (InputMismatchException e) {
            logger.error("Validation error during payment processing: {}", e.getMessage(), e);
            model.addAttribute("error", "Invalid payment details.");
            return "buyer/paymentForm";  // Show form with error
        } catch (Exception e) {
            logger.error("Error occurred while processing payment for amount: {}", amount, e);
            throw new RuntimeException("Failed to process payment.");
        }
    }

    @GetMapping("/processPayment")
    public String viewPaymentPage(@RequestParam("totalPrice") double totalPrice, Model model) {
        logger.info("Navigating to payment form with total price: {}", totalPrice);

        try {
            model.addAttribute("totalPrice", totalPrice);
            return "buyer/paymentForm";  // Return the JSP page
        } catch (Exception e) {
            logger.error("Error occurred while loading payment form", e);
            throw new RuntimeException("Failed to load payment form.");
        }
    }

    @PostMapping("/successPayment")
    public String successPayment() {
        logger.info("Navigating to success payment page");
        return "buyer/successPayment";  // Return the success JSP page
    }

    // Step 3: Global exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
