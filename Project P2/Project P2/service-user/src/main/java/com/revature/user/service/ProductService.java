package com.revature.user.service;

import java.util.List;
import java.util.Locale.Category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.user.model.ProductRequest;
import com.revature.user.model.ProductResponse;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Fetch all products
    public List<ProductResponse> getAllProducts() {
        logger.info("Fetching all products");

        try {
            List<ProductResponse> products = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8087/products")  // API Gateway URL for Product Service
                    .retrieve()
                    .bodyToFlux(ProductResponse.class)
                    .collectList()
                    .block();

            logger.info("Fetched {} products", products.size());
            return products;
        } catch (Exception e) {
            logger.error("Error while fetching products", e);
            throw new RuntimeException("Failed to fetch products.");
        }
    }

    // Fetch a product by ID
    public ProductResponse getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);

        try {
            ProductResponse product = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8087/products/" + id)
                    .retrieve()
                    .bodyToMono(ProductResponse.class)
                    .block();

            logger.info("Fetched product with ID: {}", id);
            return product;
        } catch (Exception e) {
            logger.error("Error while fetching product with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch product.");
        }
    }

    // Add a new product
    public String addProduct(ProductRequest productRequest) {
        logger.info("Adding new product: {}", productRequest.getName());

        try {
            String response = webClientBuilder.build()
                    .post()
                    .uri("http://localhost:8087/products")  // API Gateway URL for adding a product
                    .bodyValue(productRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            logger.info("Product added successfully: {}", productRequest.getName());
            return response;
        } catch (Exception e) {
            logger.error("Error while adding product: {}", productRequest.getName(), e);
            throw new RuntimeException("Failed to add product.");
        }
    }

    // Fetch all categories for adding a product
    public List<Category> getAllCategories() {
        logger.info("Fetching all categories");

        try {
            List<Category> categories = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8087/categories")  // API Gateway URL for fetching categories
                    .retrieve()
                    .bodyToFlux(Category.class)
                    .collectList()
                    .block();

            logger.info("Fetched {} categories", categories.size());
            return categories;
        } catch (Exception e) {
            logger.error("Error while fetching categories", e);
            throw new RuntimeException("Failed to fetch categories.");
        }
    }
}
