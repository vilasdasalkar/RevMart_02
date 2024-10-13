package com.revature.product.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.revature.product.model.Product;
import com.revature.product.service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:8080")
public class ProductController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    // Step 2: Add logging and exception handling for createProduct
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        logger.info("Creating new product: {}", product.getName());

        try {
            Product createdProduct = productService.createProduct(product);
            logger.info("Product created successfully with ID: {}", createdProduct.getId());
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            logger.error("Error while creating product: {}", product, e);
            throw new RuntimeException("Failed to create product.");
        }
    }

    // Step 3: Add logging and exception handling for getAllProducts
    @GetMapping
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");

        try {
            return productService.getAllProducts();
        } catch (Exception e) {
            logger.error("Error while fetching all products", e);
            throw new RuntimeException("Failed to fetch products.");
        }
    }

    // Step 4: Add logging and exception handling for getProductById
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);

        try {
            Optional<Product> product = productService.getProductById(id);
            return product.map(ResponseEntity::ok)
                          .orElseGet(() -> {
                              logger.warn("Product not found with ID: {}", id);
                              return ResponseEntity.notFound().build();
                          });
        } catch (Exception e) {
            logger.error("Error while fetching product with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch product.");
        }
    }

    // Step 5: Add logging and exception handling for updateProduct
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        logger.info("Updating product with ID: {}", id);

        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            logger.info("Product updated successfully with ID: {}", updatedProduct.getId());
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            logger.error("Error while updating product with ID: {}", id, e);
            throw new RuntimeException("Failed to update product.");
        }
    }

    // Step 6: Add logging and exception handling for deleteProduct
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);

        try {
            productService.deleteProduct(id);
            logger.info("Product deleted successfully with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error while deleting product with ID: {}", id, e);
            throw new RuntimeException("Failed to delete product.");
        }
    }

    // Step 7: Add logging and exception handling for getProductsByCategoryId
    @GetMapping("/categoryProduct/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        logger.info("Fetching products for category ID: {}", categoryId);

        try {
            List<Product> products = productService.getProductsByCategoryId(categoryId);
            if (products.isEmpty()) {
                logger.warn("No products found for category ID: {}", categoryId);
                return ResponseEntity.noContent().build();
            }
            logger.info("Products fetched successfully for category ID: {}", categoryId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error while fetching products for category ID: {}", categoryId, e);
            throw new RuntimeException("Failed to fetch products by category.");
        }
    }

    // Step 8: Global exception handler for handling exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
