package com.revature.user.controller;

import com.revature.user.model.Order;
import com.revature.user.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private Model model;

    // Test for showPage (Success)
    @Test
    public void testShowPage_Success() {
        String viewName = orderController.showPage();
        assertEquals("buyer/orders", viewName);
    }

    // Test for getOrderById (Success)
    @Test
    public void testGetOrderById_Success() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderService.getOrderById(orderId)).thenReturn(order);

        String viewName = orderController.getOrderById(orderId, model);

        verify(model, times(1)).addAttribute("order", order);
        assertEquals("order-details", viewName);
    }

    // Test for getOrderById (Failure)
    @Test
    public void testGetOrderById_Failure() {
        Long orderId = 1L;

        when(orderService.getOrderById(orderId)).thenThrow(new RuntimeException("Error fetching order with id: " + orderId));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderController.getOrderById(orderId, model);
        });

        assertEquals("Error fetching order with id: " + orderId, exception.getMessage());
    }

    // Test for createOrderPage (Success)
    @Test
    public void testCreateOrderPage_Success() {
        String viewName = orderController.createOrderPage();
        assertEquals("create-order", viewName);
    }

    // Test for createOrder (Success)
    @Test
    public void testCreateOrder_Success() {
        Order order = new Order();
        order.setId(1L);

        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        String viewName = orderController.createOrder(order, model);

        verify(model, times(1)).addAttribute("order", order);
        assertEquals("redirect:/frontend/orders/1", viewName);
    }

    // Test for createOrder (Failure)
    @Test
    public void testCreateOrder_Failure() {
        Order order = new Order();

        when(orderService.createOrder(any(Order.class))).thenThrow(new RuntimeException("Error creating order"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderController.createOrder(order, model);
        });

        assertEquals("Error creating order.", exception.getMessage());
    }

    // Test for updateOrderStatus (Success)
    @Test
    public void testUpdateOrderStatus_Success() {
        Long orderId = 1L;
        String status = "SHIPPED";
        Order updatedOrder = new Order();
        updatedOrder.setId(orderId);
        updatedOrder.setStatus(status);

        when(orderService.updateOrderStatus(orderId, status)).thenReturn(updatedOrder);

        String viewName = orderController.updateOrderStatus(orderId, status, model);

        verify(model, times(1)).addAttribute("order", updatedOrder);
        assertEquals("redirect:/frontend/orders/1", viewName);
    }

    // Test for updateOrderStatus (Failure)
    @Test
    public void testUpdateOrderStatus_Failure() {
        Long orderId = 1L;
        String status = "SHIPPED";

        when(orderService.updateOrderStatus(orderId, status)).thenThrow(new RuntimeException("Error updating order status"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderController.updateOrderStatus(orderId, status, model);
        });

        assertEquals("Error updating order status.", exception.getMessage());
    }

    // Test for handleException (Success)
    @Test
    public void testHandleException_Success() {
        Exception exception = new Exception("Some error occurred");

        ResponseEntity<String> response = orderController.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Some error occurred", response.getBody());
    }
}
