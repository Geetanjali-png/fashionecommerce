package com.geetanjali.fashionecommerce.controller;

import com.geetanjali.fashionecommerce.entity.Order;
import com.geetanjali.fashionecommerce.entity.User;
import com.geetanjali.fashionecommerce.service.CartService;
import com.geetanjali.fashionecommerce.service.OrderService;
import com.geetanjali.fashionecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    private User getLoggedInUser(Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("User is not logged in");
        }

        return userService
                .getUserByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @GetMapping("/checkout")
    public String checkout(
            Authentication authentication,
            Model model) {

        User user = getLoggedInUser(authentication);

        var cart = cartService.getOrCreateCart(user);

        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cart", cart);

        model.addAttribute(
                "total",
                cartService.getCartTotal(cart)
        );

        return "checkout";
    }

    @PostMapping("/checkout/place-order")
    public String placeOrder(
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        Order order = orderService.placeOrder(user);

        return "redirect:/order-confirmation/" + order.getId();
    }

    @GetMapping("/order-confirmation/{id}")
    public String orderConfirmation(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        User user = getLoggedInUser(authentication);

        Order order = orderService.getOrderById(id);

        if (!order.getUser().getId().equals(user.getId())) {
            return "redirect:/products";
        }

        model.addAttribute("order", order);

        return "order-confirmation";
    }

    @GetMapping("/orders")
    public String myOrders(
            Authentication authentication,
            Model model) {

        User user = getLoggedInUser(authentication);

        model.addAttribute(
                "orders",
                orderService.getUserOrders(user)
        );

        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String orderDetails(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        User user = getLoggedInUser(authentication);

        Order order = orderService.getOrderById(id);

        if (!order.getUser().getId().equals(user.getId())) {
            return "redirect:/orders";
        }

        model.addAttribute("order", order);

        return "order-details";
    }
}