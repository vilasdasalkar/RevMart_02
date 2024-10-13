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

import com.revature.product.model.Category;
import com.revature.product.service.CategoryService;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:8080")
public class CategoryController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    // Step 2: Add logging and exception handling for createCategory
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        logger.info("Creating new category: {}", category.getName());

        try {
            Category createdCategory = categoryService.createCategory(category);
            logger.info("Category created successfully with ID: {}", createdCategory.getId());
            return ResponseEntity.ok(createdCategory);
        } catch (Exception e) {
            logger.error("Error while creating category: {}", category, e);
            throw new RuntimeException("Failed to create category.");
        }
    }

    // Step 3: Add logging and exception handling for getAllCategories
    @GetMapping
    public List<Category> getAllCategories() {
        logger.info("Fetching all categories");

        try {
            return categoryService.getAllCategories();
        } catch (Exception e) {
            logger.error("Error while fetching all categories", e);
            throw new RuntimeException("Failed to fetch categories.");
        }
    }

    // Step 4: Add logging and exception handling for getCategoryById
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        logger.info("Fetching category with ID: {}", id);

        try {
            Optional<Category> category = categoryService.getCategoryById(id);
            return category.map(ResponseEntity::ok)
                           .orElseGet(() -> {
                               logger.warn("Category not found with ID: {}", id);
                               return ResponseEntity.notFound().build();
                           });
        } catch (Exception e) {
            logger.error("Error while fetching category with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch category.");
        }
    }

    // Step 5: Add logging and exception handling for updateCategory
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        logger.info("Updating category with ID: {}", id);

        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
            logger.info("Category updated successfully with ID: {}", id);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            logger.error("Error while updating category with ID: {}", id, e);
            throw new RuntimeException("Failed to update category.");
        }
    }

    // Step 6: Add logging and exception handling for deleteCategory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Deleting category with ID: {}", id);

        try {
            categoryService.deleteCategory(id);
            logger.info("Category deleted successfully with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error while deleting category with ID: {}", id, e);
            throw new RuntimeException("Failed to delete category.");
        }
    }

    // Step 7: Global exception handler for handling exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
