package com.revature.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.user.model.OrderResponse;

public interface OrderRepository extends JpaRepository<OrderResponse, Long> {
	    // JpaRepository provides basic CRUD operations
	}

