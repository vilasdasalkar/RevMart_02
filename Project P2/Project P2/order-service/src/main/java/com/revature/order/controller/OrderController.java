package com.revature.order.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.order.model.Order;
import com.revature.order.model.OrderStatus;
import com.revature.order.service.OrderService;

import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/orders")
public class OrderController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Step 2: Add logging and exception handling for createOrder
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        logger.info("Creating a new order: {}", order);

        try {
            Order createdOrder = orderService.createOrder(order);
            logger.info("Order created successfully with ID: {}", createdOrder.getId());
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            logger.error("Error while creating order: {}", order, e);
            throw new RuntimeException("Failed to create order.");
        }
    }

    // Step 3: Add logging and exception handling for getUserInfo
    @GetMapping("/user/{userId}")
    public Mono<ResponseEntity<String>> getUserInfo(@PathVariable String userId) {
        logger.info("Fetching user info for userId: {}", userId);

        try {
            return orderService.getUserInfo(userId)
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching user info for userId: {}", userId, e);
            throw new RuntimeException("Failed to fetch user info.");
        }
    }

    // Step 4: Add logging and exception handling for getProductInfo
    @GetMapping("/product/{productId}")
    public Mono<ResponseEntity<String>> getProductInfo(@PathVariable String productId) {
        logger.info("Fetching product info for productId: {}", productId);

        try {
            return orderService.getProductInfo(productId)
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching product info for productId: {}", productId, e);
            throw new RuntimeException("Failed to fetch product info.");
        }
    }

    // Step 5: Add logging and exception handling for getCartInfo
    @GetMapping("/cart/{cartId}")
    public Mono<ResponseEntity<String>> getCartInfo(@PathVariable String cartId) {
        logger.info("Fetching cart info for cartId: {}", cartId);

        try {
            return orderService.getCartInfo(cartId)
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching cart info for cartId: {}", cartId, e);
            throw new RuntimeException("Failed to fetch cart info.");
        }
    }

    // Step 6: Add logging and exception handling for getOrderById
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        logger.info("Fetching order by ID: {}", id);

        try {
            Optional<Order> order = orderService.getOrderById(id);
            return order.map(ResponseEntity::ok)
                        .orElseGet(() -> {
                            logger.warn("Order not found with ID: {}", id);
                            return ResponseEntity.notFound().build();
                        });
        } catch (Exception e) {
            logger.error("Error fetching order by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch order.");
        }
    }

    // Step 7: Add logging and exception handling for updateOrderStatus
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody Order order) {
        logger.info("Updating order status for order ID: {}", id);

        try {
            Optional<Order> updatedOrder = orderService.updateOrderStatus(id, order.getStatus());
            return updatedOrder.map(ResponseEntity::ok)
                               .orElseGet(() -> {
                                   logger.warn("Order not found for ID: {}", id);
                                   return ResponseEntity.notFound().build();
                               });
        } catch (Exception e) {
            logger.error("Error updating order status for order ID: {}", id, e);
            throw new RuntimeException("Failed to update order status.");
        }
    }

    // Step 8: Global exception handler for handling exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
