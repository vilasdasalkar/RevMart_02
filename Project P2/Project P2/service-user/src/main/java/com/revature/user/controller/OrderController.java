package com.revature.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.revature.user.model.Order;
import com.revature.user.service.OrderService;

@Controller
@RequestMapping("/frontend/orders")
public class OrderController {

    // Step 1: Create a Logger instance
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public String showPage() {
        logger.info("Navigating to the buyer orders page.");
        return "buyer/orders";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        logger.info("Fetching order with id: {}", id);

        try {
            Order order = orderService.getOrderById(id);
            model.addAttribute("order", order);
            logger.info("Order fetched successfully: {}", order);
            return "order-details";  // JSP page to display order details
        } catch (Exception e) {
            logger.error("Error fetching order with id: {}", id, e);
            throw new RuntimeException("Error fetching order with id: " + id);
        }
    }

    @GetMapping("/create")
    public String createOrderPage() {
        logger.info("Navigating to the create order page.");
        return "create-order"; // JSP page to create an order
    }

    @PostMapping
    public String createOrder(@ModelAttribute("order") Order order, Model model) {
        logger.info("Creating a new order: {}", order);

        try {
            Order createdOrder = orderService.createOrder(order);
            model.addAttribute("order", createdOrder);
            logger.info("Order created successfully with id: {}", createdOrder.getId());
            return "redirect:/frontend/orders/" + createdOrder.getId();
        } catch (Exception e) {
            logger.error("Error creating order: {}", order, e);
            throw new RuntimeException("Error creating order.");
        }
    }

    @PostMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam("status") String status, Model model) {
        logger.info("Updating order status for id: {} to status: {}", id, status);

        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            model.addAttribute("order", updatedOrder);
            logger.info("Order status updated successfully for id: {}", id);
            return "redirect:/frontend/orders/" + updatedOrder.getId();
        } catch (Exception e) {
            logger.error("Error updating status for order with id: {}", id, e);
            throw new RuntimeException("Error updating order status.");
        }
    }

    // Step 3: Exception handler for handling general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
