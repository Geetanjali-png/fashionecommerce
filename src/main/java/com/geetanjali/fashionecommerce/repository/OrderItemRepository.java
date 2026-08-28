package com.geetanjali.fashionecommerce.repository;

import com.geetanjali.fashionecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {
}