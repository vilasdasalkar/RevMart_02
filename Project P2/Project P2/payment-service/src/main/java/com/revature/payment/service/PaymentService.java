package com.revature.payment.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.payment.model.PaymentDetails;
import com.revature.payment.model.PaymentStatus;
import com.revature.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String userServiceUrl = "http://service-user/users";
    private final String productServiceUrl = "http://product-service/products";
    private final String cartServiceUrl = "http://cart-service/cart";
    private final String orderServiceUrl = "http://order-service/orders";

    // Create a new payment
    public PaymentDetails createPayment(PaymentDetails paymentDetails) {
        logger.info("Initiating payment creation for order ID: {}", paymentDetails.getOrderId());

        try {
            // Example: Call external services to validate the order
            String orderInfo = webClientBuilder.build()
                    .get()
                    .uri(orderServiceUrl + "/" + paymentDetails.getOrderId())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();  // Blocking for simplicity

            logger.info("Order info retrieved successfully for order ID: {}", paymentDetails.getOrderId());

            // Save payment details after fetching external service data
            paymentDetails.setStatus(PaymentStatus.INITIATED);
            PaymentDetails savedPayment = paymentRepository.save(paymentDetails);
            logger.info("Payment created successfully with ID: {}", savedPayment.getId());

            return savedPayment;
        } catch (Exception e) {
            logger.error("Error while creating payment for order ID: {}", paymentDetails.getOrderId(), e);
            throw new RuntimeException("Failed to create payment.");
        }
    }

    // Update payment status
    public PaymentDetails updatePaymentStatus(Long paymentId, PaymentStatus status) {
        logger.info("Updating payment status for payment ID: {}", paymentId);

        try {
            Optional<PaymentDetails> paymentOpt = paymentRepository.findById(paymentId);
            if (paymentOpt.isPresent()) {
                PaymentDetails payment = paymentOpt.get();
                payment.setStatus(status);
                PaymentDetails updatedPayment = paymentRepository.save(payment);
                logger.info("Payment status updated successfully for payment ID: {}", paymentId);
                return updatedPayment;
            } else {
                logger.warn("Payment not found with ID: {}", paymentId);
                throw new IllegalArgumentException("Payment not found");
            }
        } catch (Exception e) {
            logger.error("Error while updating payment status for payment ID: {}", paymentId, e);
            throw new RuntimeException("Failed to update payment status.");
        }
    }

    // Get payment details by ID
    public Optional<PaymentDetails> getPaymentDetails(Long id) {
        logger.info("Fetching payment details for payment ID: {}", id);

        try {
            return paymentRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error while fetching payment details for payment ID: {}", id, e);
            throw new RuntimeException("Failed to fetch payment details.");
        }
    }

    // Fetch user info from the User Service
    public String getUserInfo(String userId) {
        logger.info("Fetching user info for user ID: {}", userId);

        try {
            return webClientBuilder.build()
                    .get()
                    .uri(userServiceUrl + "/" + userId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            logger.error("Error while fetching user info for user ID: {}", userId, e);
            throw new RuntimeException("Failed to fetch user info.");
        }
    }
}
