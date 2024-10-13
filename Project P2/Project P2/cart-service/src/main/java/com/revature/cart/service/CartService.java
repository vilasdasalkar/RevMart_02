package com.revature.cart.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.cart.model.Cart;
import com.revature.cart.model.CartItem;
import com.revature.cart.repository.CartRepository;

import reactor.core.publisher.Mono;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Fetch user info from the User Service using Load Balancer
    public Mono<String> getUserInfo(Long userId) {
        logger.info("Fetching user info for user ID: {}", userId);

        try {
            Mono<String> userInfo = webClientBuilder.build()
                .get()
                .uri("http://user-service/users/" + userId)  // Load-balancing with lb://user-service
                .retrieve()
                .bodyToMono(String.class);

            logger.info("User info fetched successfully for user ID: {}", userId);
            return userInfo;
        } catch (Exception e) {
            logger.error("Error while fetching user info for user ID: {}", userId, e);
            throw new RuntimeException("Failed to fetch user info.");
        }
    }

    // Fetch product info from the Product Service using Load Balancer
    public Mono<String> getProductInfo(Long productId) {
        logger.info("Fetching product info for product ID: {}", productId);

        try {
            Mono<String> productInfo = webClientBuilder.build()
                .get()
                .uri("http://product-service/products/" + productId)  // Load-balancing with lb://product-service
                .retrieve()
                .bodyToMono(String.class);

            logger.info("Product info fetched successfully for product ID: {}", productId);
            return productInfo;
        } catch (Exception e) {
            logger.error("Error while fetching product info for product ID: {}", productId, e);
            throw new RuntimeException("Failed to fetch product info.");
        }
    }

    // Add a new cart
    public Cart addCart(Cart cart) {
        logger.info("Adding a new cart for user ID: {}", cart.getUserId());

        try {
            Cart savedCart = cartRepository.save(cart);
            logger.info("Cart added successfully for user ID: {}", cart.getUserId());
            return savedCart;
        } catch (Exception e) {
            logger.error("Error while adding cart for user ID: {}", cart.getUserId(), e);
            throw new RuntimeException("Failed to add cart.");
        }
    }

    // Get cart by user ID
    public List<Cart> getCartByUserId(Long userId) {
        logger.info("Fetching carts for user ID: {}", userId);

        try {
            List<Cart> carts = cartRepository.findByUserId(userId);
            logger.info("Fetched {} carts for user ID: {}", carts.size(), userId);
            return carts;
        } catch (Exception e) {
            logger.error("Error while fetching carts for user ID: {}", userId, e);
            throw new RuntimeException("Failed to fetch carts.");
        }
    }

    // Update a cart
    public Cart updateCart(Cart cart) {
        logger.info("Updating cart for user ID: {}", cart.getUserId());

        try {
            Cart updatedCart = cartRepository.save(cart);
            logger.info("Cart updated successfully for user ID: {}", cart.getUserId());
            return updatedCart;
        } catch (Exception e) {
            logger.error("Error while updating cart for user ID: {}", cart.getUserId(), e);
            throw new RuntimeException("Failed to update cart.");
        }
    }

    // Delete a cart
    public void deleteCart(Long cartId) {
        logger.info("Deleting cart with ID: {}", cartId);

        try {
            cartRepository.deleteById(cartId);
            logger.info("Cart deleted successfully with ID: {}", cartId);
        } catch (Exception e) {
            logger.error("Error while deleting cart with ID: {}", cartId, e);
            throw new RuntimeException("Failed to delete cart.");
        }
    }

    // Add item to cart
    public void addItemToCart(Long userId, CartItem cartItem) {
        logger.info("Adding item to cart for user ID: {}", userId);

        try {
            List<Cart> carts = cartRepository.findByUserId(userId);

            Cart cart;
            if (carts.isEmpty()) {
                logger.info("No existing cart for user ID: {}. Creating a new cart.", userId);
                cart = new Cart();
                cart.setUserId(userId);
            } else {
                cart = carts.get(0);  // Assuming the first cart is the one you want
                logger.info("Adding item to existing cart for user ID: {}", userId);
            }

            cart.getCartItems().add(cartItem);
            cartRepository.save(cart);
            logger.info("Item added to cart for user ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error while adding item to cart for user ID: {}", userId, e);
            throw new RuntimeException("Failed to add item to cart.");
        }
    }
}
