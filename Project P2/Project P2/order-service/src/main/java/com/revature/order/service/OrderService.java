package com.revature.order.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.order.client.UserClientService;
import com.revature.order.model.Order;
import com.revature.order.model.OrderStatus;
import com.revature.order.repository.OrderRepository;

import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final UserClientService userClientService;

    public OrderService(OrderRepository orderRepository, UserClientService userClientService) {
        this.orderRepository = orderRepository;
        this.userClientService = userClientService;
    }

    // Create a new order
    public Order createOrder(Order order) {
        logger.info("Creating a new order for user ID: {}", order.getUserId());

        try {
            Order savedOrder = orderRepository.save(order);
            logger.info("Order created successfully with ID: {}", savedOrder.getId());
            return savedOrder;
        } catch (Exception e) {
            logger.error("Error while creating order for user ID: {}", order.getUserId(), e);
            throw new RuntimeException("Failed to create order.");
        }
    }

    // Get user info from UserService
    public Mono<String> getUserInfo(String userId) {
        logger.info("Fetching user info for user ID: {}", userId);

        try {
            return userClientService.getUserById(userId);
        } catch (Exception e) {
            logger.error("Error while fetching user info for user ID: {}", userId, e);
            throw new RuntimeException("Failed to fetch user info.");
        }
    }

    // Get product info from ProductService
    public Mono<String> getProductInfo(String productId) {
        logger.info("Fetching product info for product ID: {}", productId);

        try {
            return WebClient.create("http://product-service")
                    .get()
                    .uri("/products/{id}", productId)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch (Exception e) {
            logger.error("Error while fetching product info for product ID: {}", productId, e);
            throw new RuntimeException("Failed to fetch product info.");
        }
    }

    // Update order status
    public Optional<Order> updateOrderStatus(Long id, OrderStatus status) {
        logger.info("Updating order status for order ID: {}", id);

        try {
            Optional<Order> existingOrder = orderRepository.findById(id);
            if (existingOrder.isPresent()) {
                Order order = existingOrder.get();
                order.setStatus(status);
                orderRepository.save(order);
                logger.info("Order status updated successfully for order ID: {}", id);
                return Optional.of(order);
            } else {
                logger.warn("Order not found with ID: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Error while updating order status for order ID: {}", id, e);
            throw new RuntimeException("Failed to update order status.");
        }
    }

    // Get cart info from CartService
    public Mono<String> getCartInfo(String cartId) {
        logger.info("Fetching cart info for cart ID: {}", cartId);

        try {
            return WebClient.create("http://cart-service")
                    .get()
                    .uri("/cart/{id}", cartId)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch (Exception e) {
            logger.error("Error while fetching cart info for cart ID: {}", cartId, e);
            throw new RuntimeException("Failed to fetch cart info.");
        }
    }

    // Find order by ID
    public Optional<Order> getOrderById(Long id) {
        logger.info("Fetching order with ID: {}", id);

        try {
            return orderRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error while fetching order with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch order.");
        }
    }
}
