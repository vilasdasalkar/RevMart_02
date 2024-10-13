package com.revature.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.user.model.PaymentDetails;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public String processPayment(PaymentDetails paymentDetails) {
        logger.info("Processing payment for amount: {}", paymentDetails.getAmount());

        try {
            // Use WebClient to send a request to the payment backend
            String response = webClientBuilder.build()
                    .post()
                    .uri("http://payment-service/payment/process")  // URL through API Gateway
                    .bodyValue(paymentDetails)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();  // Blocking for simplicity; can be improved with reactive approach

            logger.info("Payment processed successfully: {}", response);
            return response;  // Return response from backend (e.g., "Payment Successful")
        } catch (Exception e) {
            logger.error("Error while processing payment: {}", paymentDetails, e);
            throw new RuntimeException("Failed to process payment.");
        }
    }
}
