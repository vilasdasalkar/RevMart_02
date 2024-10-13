package com.revature.product.service;

import com.revature.product.model.Product;
import com.revature.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    // Create a new product
    public Product createProduct(Product product) {
        logger.info("Creating a new product: {}", product.getName());

        try {
            Product savedProduct = productRepository.save(product);
            logger.info("Product created successfully with ID: {}", savedProduct.getId());
            return savedProduct;
        } catch (Exception e) {
            logger.error("Error while creating product: {}", product.getName(), e);
            throw new RuntimeException("Failed to create product.");
        }
    }

    // Get all products
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");

        try {
            List<Product> products = productRepository.findAll();
            logger.info("Fetched {} products", products.size());
            return products;
        } catch (Exception e) {
            logger.error("Error while fetching products", e);
            throw new RuntimeException("Failed to fetch products.");
        }
    }

    // Get a product by ID
    public Optional<Product> getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);

        try {
            return productRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error while fetching product with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch product.");
        }
    }

    // Update an existing product
    public Product updateProduct(Long id, Product productDetails) {
        logger.info("Updating product with ID: {}", id);

        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Product not found with ID: {}", id);
                        return new RuntimeException("Product not found with id " + id);
                    });

            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setSkuCode(productDetails.getSkuCode());
            product.setPrice(productDetails.getPrice());
            product.setCategory(productDetails.getCategory());
            product.setImageurl(productDetails.getImageurl());

            Product updatedProduct = productRepository.save(product);
            logger.info("Product updated successfully with ID: {}", id);
            return updatedProduct;
        } catch (Exception e) {
            logger.error("Error while updating product with ID: {}", id, e);
            throw new RuntimeException("Failed to update product.");
        }
    }

    // Delete a product
    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);

        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Product not found with ID: {}", id);
                        return new RuntimeException("Product not found with id " + id);
                    });

            productRepository.delete(product);
            logger.info("Product deleted successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error while deleting product with ID: {}", id, e);
            throw new RuntimeException("Failed to delete product.");
        }
    }

    // Get products by category ID
    public List<Product> getProductsByCategoryId(Long categoryId) {
        logger.info("Fetching products for category ID: {}", categoryId);

        try {
            List<Product> products = productRepository.findByCategoryId(categoryId);
            logger.info("Fetched {} products for category ID: {}", products.size(), categoryId);
            return products;
        } catch (Exception e) {
            logger.error("Error while fetching products for category ID: {}", categoryId, e);
            throw new RuntimeException("Failed to fetch products by category.");
        }
    }
}
