package com.geetanjali.fashionecommerce.repository;

import com.geetanjali.fashionecommerce.entity.Order;
import com.geetanjali.fashionecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserOrderByOrderDateDesc(User user);
}