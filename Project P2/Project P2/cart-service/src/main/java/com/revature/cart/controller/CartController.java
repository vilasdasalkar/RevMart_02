package com.revature.cart.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.cart.model.Cart;
import com.revature.cart.model.CartItem;
import com.revature.cart.service.CartService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:8080")
public class CartController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    // Step 2: Add logging and exception handling for adding a cart
    @PostMapping("/add")
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        logger.info("Adding new cart: {}", cart);

        try {
            Cart createdCart = cartService.addCart(cart);
            logger.info("Cart created successfully with id: {}", createdCart.getId());
            return ResponseEntity.ok(createdCart);
        } catch (Exception e) {
            logger.error("Error while adding cart", e);
            throw new RuntimeException("Failed to add cart");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getCartByUserId(@PathVariable Long userId) {
        logger.info("Fetching carts for userId: {}", userId);

        try {
            List<Cart> carts = cartService.getCartByUserId(userId);
            if (carts.isEmpty()) {
                logger.warn("No carts found for userId: {}", userId);
                return ResponseEntity.notFound().build();
            }
            logger.info("Carts found for userId: {}", userId);
            return ResponseEntity.ok(carts);
        } catch (Exception e) {
            logger.error("Error while fetching carts for userId: {}", userId, e);
            throw new RuntimeException("Failed to fetch carts");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {
        logger.info("Updating cart: {}", cart);

        try {
            Cart updatedCart = cartService.updateCart(cart);
            logger.info("Cart updated successfully with id: {}", updatedCart.getId());
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            logger.error("Error while updating cart", e);
            throw new RuntimeException("Failed to update cart");
        }
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        logger.info("Deleting cart with id: {}", cartId);

        try {
            cartService.deleteCart(cartId);
            logger.info("Cart deleted successfully with id: {}", cartId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error while deleting cart with id: {}", cartId, e);
            throw new RuntimeException("Failed to delete cart");
        }
    }

    @PostMapping("/addItem/{userId}")
    public ResponseEntity<Void> addItemToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        logger.info("Adding item to cart for userId: {}", userId);

        try {
            cartService.addItemToCart(userId, cartItem);
            logger.info("Item added successfully to userId: {}", userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error while adding item to cart for userId: {}", userId, e);
            throw new RuntimeException("Failed to add item to cart");
        }
    }

    // Fetch user and product information
    @GetMapping("/user-info/{userId}")
    public ResponseEntity<String> getUserInfo(@PathVariable Long userId) {
        logger.info("Fetching user info for userId: {}", userId);

        try {
            return cartService.getUserInfo(userId)
                .map(ResponseEntity::ok)
                .block();
        } catch (Exception e) {
            logger.error("Error fetching user info for userId: {}", userId, e);
            throw new RuntimeException("Failed to fetch user info");
        }
    }

    @GetMapping("/product-info/{productId}")
    public ResponseEntity<String> getProductInfo(@PathVariable Long productId) {
        logger.info("Fetching product info for productId: {}", productId);

        try {
            return cartService.getProductInfo(productId)
                .map(ResponseEntity::ok)
                .block();
        } catch (Exception e) {
            logger.error("Error fetching product info for productId: {}", productId, e);
            throw new RuntimeException("Failed to fetch product info");
        }
    }

    // Step 3: Global exception handler for handling exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
