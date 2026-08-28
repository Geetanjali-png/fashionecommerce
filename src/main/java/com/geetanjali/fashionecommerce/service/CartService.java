package com.geetanjali.fashionecommerce.service;

import com.geetanjali.fashionecommerce.entity.Cart;
import com.geetanjali.fashionecommerce.entity.CartItem;
import com.geetanjali.fashionecommerce.entity.Product;
import com.geetanjali.fashionecommerce.entity.ProductVariant;
import com.geetanjali.fashionecommerce.entity.User;
import com.geetanjali.fashionecommerce.repository.CartItemRepository;
import com.geetanjali.fashionecommerce.repository.CartRepository;
import com.geetanjali.fashionecommerce.repository.ProductRepository;
import com.geetanjali.fashionecommerce.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    public Cart getOrCreateCart(User user) {

        Optional<Cart> existingCart =
                cartRepository.findByUserId(user.getId());

        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        Cart cart = new Cart(user);

        return cartRepository.save(cart);
    }

    public void addToCart(
            User user,
            Long productId,
            Long variantId,
            int quantity) {

        Cart cart = getOrCreateCart(user);

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        ProductVariant variant = null;

        if (variantId != null) {
            variant = productVariantRepository
                    .findById(variantId)
                    .orElseThrow(() ->
                            new RuntimeException("Variant not found"));

            if (variant.getStock() < quantity) {
                throw new RuntimeException("Not enough stock available");
            }
        }

        Optional<CartItem> existingItem =
                cartItemRepository.findByCartIdAndProductId(
                        cart.getId(),
                        productId
                );

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();

            item.setQuantity(
                    item.getQuantity() + quantity
            );

            cartItemRepository.save(item);

        } else {

            CartItem item = new CartItem(
                    cart,
                    product,
                    variant,
                    quantity
            );

            cartItemRepository.save(item);
        }
    }

    public void addToCart(
            User user,
            Long productId,
            int quantity) {

        addToCart(
                user,
                productId,
                null,
                quantity
        );
    }

    public void updateQuantity(
            User user,
            Long itemId,
            int quantity) {

        CartItem item = cartItemRepository
                .findById(itemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found"));

        if (quantity <= 0) {

            cartItemRepository.delete(item);

        } else {

            item.setQuantity(quantity);

            cartItemRepository.save(item);
        }
    }

    public void removeItem(
            User user,
            Long itemId) {

        CartItem item = cartItemRepository
                .findById(itemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found"));

        cartItemRepository.delete(item);
    }

    public double getCartTotal(Cart cart) {

        return cart.getItems()
                .stream()
                .mapToDouble(item -> {

                    double price;

                    if (item.getVariant() != null) {
                        price = item.getVariant().getPrice();
                    } else {
                        price = item.getProduct().getPrice();
                    }

                    return price * item.getQuantity();

                })
                .sum();
    }
}