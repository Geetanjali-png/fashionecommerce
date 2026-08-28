package com.geetanjali.fashionecommerce.controller;

import com.geetanjali.fashionecommerce.entity.Product;
import com.geetanjali.fashionecommerce.entity.Review;
import com.geetanjali.fashionecommerce.entity.User;
import com.geetanjali.fashionecommerce.repository.ProductRepository;
import com.geetanjali.fashionecommerce.repository.UserRepository;
import com.geetanjali.fashionecommerce.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/reviews/add/{productId}")
    public String addReview(
            @PathVariable Long productId,
            @RequestParam Integer rating,
            @RequestParam String comment,
            Authentication authentication) {

        Product product = productRepository
                .findById(productId)
                .orElse(null);

        if (product == null || authentication == null) {
            return "redirect:/products";
        }

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setProduct(product);
        review.setUser(user);

        reviewService.saveReview(review);

        return "redirect:/products/" + productId;
    }
}