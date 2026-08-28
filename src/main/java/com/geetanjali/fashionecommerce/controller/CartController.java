package com.geetanjali.fashionecommerce.controller;

import com.geetanjali.fashionecommerce.entity.Cart;
import com.geetanjali.fashionecommerce.entity.User;
import com.geetanjali.fashionecommerce.service.CartService;
import com.geetanjali.fashionecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    private User getLoggedInUser(Authentication authentication) {

        return userService
                .getUserByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @GetMapping
    public String viewCart(
            Authentication authentication,
            Model model) {

        User user = getLoggedInUser(authentication);

        Cart cart = cartService.getOrCreateCart(user);

        model.addAttribute("cart", cart);

        model.addAttribute(
                "total",
                cartService.getCartTotal(cart)
        );

        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        cartService.addToCart(
                user,
                productId,
                quantity
        );

        return "redirect:/cart";
    }

    @PostMapping("/update/{itemId}")
    public String updateCart(
            @PathVariable Long itemId,
            @RequestParam int quantity,
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        cartService.updateQuantity(
                user,
                itemId,
                quantity
        );

        return "redirect:/cart";
    }

    @PostMapping("/remove/{itemId}")
    public String removeItem(
            @PathVariable Long itemId,
            Authentication authentication) {

        User user = getLoggedInUser(authentication);

        cartService.removeItem(
                user,
                itemId
        );

        return "redirect:/cart";
    }
}