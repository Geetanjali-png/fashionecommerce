package com.geetanjali.fashionecommerce.controller;

import com.geetanjali.fashionecommerce.entity.Product;
import com.geetanjali.fashionecommerce.service.ProductService;
import com.geetanjali.fashionecommerce.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping("/products")
    public String products(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "category", required = false) Long categoryId,
            @RequestParam(value = "brand", required = false) Long brandId,
            Model model) {

        List<Product> products;

        if (categoryId != null) {

            products = productService
                    .getProductsByCategory(categoryId);

        } else if (brandId != null) {

            products = productService
                    .getProductsByBrand(brandId);

        } else if (search != null && !search.trim().isEmpty()) {

            products = productService
                    .searchProducts(search);

        } else {

            products = productService
                    .getAllProducts();
        }

        model.addAttribute("products", products);
        model.addAttribute("search", search);

        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetails(
            @PathVariable Long id,
            Model model) {

        Product product = productService
                .getProductById(id)
                .orElse(null);

        if (product == null) {
            return "redirect:/products";
        }

        model.addAttribute("product", product);

        model.addAttribute(
                "variants",
                productVariantService.getVariantsByProductId(id)
        );

        return "product-details";
    }
}