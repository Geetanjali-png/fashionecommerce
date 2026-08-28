package com.geetanjali.fashionecommerce.service;

import com.geetanjali.fashionecommerce.entity.Cart;
import com.geetanjali.fashionecommerce.entity.CartItem;
import com.geetanjali.fashionecommerce.entity.Order;
import com.geetanjali.fashionecommerce.entity.OrderItem;
import com.geetanjali.fashionecommerce.entity.ProductVariant;
import com.geetanjali.fashionecommerce.entity.User;
import com.geetanjali.fashionecommerce.repository.CartRepository;
import com.geetanjali.fashionecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    public Order placeOrder(User user) {

        Cart cart = cartService.getOrCreateCart(user);

        if (cart.getItems() == null ||
                cart.getItems().isEmpty()) {

            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        double total = 0;

        for (CartItem cartItem : cart.getItems()) {

            double itemPrice;

            ProductVariant variant =
                    cartItem.getVariant();

            if (variant != null) {
                itemPrice = variant.getPrice();
            } else {
                itemPrice =
                        cartItem.getProduct().getPrice();
            }

            int quantity =
                    cartItem.getQuantity();

            OrderItem orderItem = new OrderItem(
                    order,
                    cartItem.getProduct(),
                    quantity,
                    itemPrice
            );

            order.getItems().add(orderItem);

            total += itemPrice * quantity;
        }

        order.setTotalAmount(total);

        Order savedOrder =
                orderRepository.save(order);

        cart.getItems().clear();

        cartRepository.save(cart);

        return savedOrder;
    }

    public List<Order> getUserOrders(User user) {

        return orderRepository
                .findByUserOrderByOrderDateDesc(user);
    }

    public Order getOrderById(Long id) {

        return orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));
    }
}