package com.revature.product.service;

import com.revature.product.model.Category;
import com.revature.product.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    // Create a new category
    public Category createCategory(Category category) {
        logger.info("Creating a new category: {}", category.getName());

        try {
            Category savedCategory = categoryRepository.save(category);
            logger.info("Category created successfully with ID: {}", savedCategory.getId());
            return savedCategory;
        } catch (Exception e) {
            logger.error("Error while creating category: {}", category.getName(), e);
            throw new RuntimeException("Failed to create category.");
        }
    }

    // Get a list of all categories
    public List<Category> getAllCategories() {
        logger.info("Fetching all categories");

        try {
            List<Category> categories = categoryRepository.findAll();
            logger.info("Fetched {} categories", categories.size());
            return categories;
        } catch (Exception e) {
            logger.error("Error while fetching categories", e);
            throw new RuntimeException("Failed to fetch categories.");
        }
    }

    // Get a category by ID
    public Optional<Category> getCategoryById(Long id) {
        logger.info("Fetching category with ID: {}", id);

        try {
            return categoryRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error while fetching category with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch category.");
        }
    }

    // Update an existing category
    public Category updateCategory(Long id, Category categoryDetails) {
        logger.info("Updating category with ID: {}", id);

        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Category not found with ID: {}", id);
                        return new RuntimeException("Category not found with id " + id);
                    });

            category.setName(categoryDetails.getName());
            category.setImageName(categoryDetails.getImageName());

            Category updatedCategory = categoryRepository.save(category);
            logger.info("Category updated successfully with ID: {}", id);
            return updatedCategory;
        } catch (Exception e) {
            logger.error("Error while updating category with ID: {}", id, e);
            throw new RuntimeException("Failed to update category.");
        }
    }

    // Delete a category
    public void deleteCategory(Long id) {
        logger.info("Deleting category with ID: {}", id);

        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Category not found with ID: {}", id);
                        return new RuntimeException("Category not found with id " + id);
                    });

            categoryRepository.delete(category);
            logger.info("Category deleted successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error while deleting category with ID: {}", id, e);
            throw new RuntimeException("Failed to delete category.");
        }
    }
}
