package com.geetanjali.fashionecommerce.controller;

import com.geetanjali.fashionecommerce.entity.Product;
import com.geetanjali.fashionecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String admin(Model model) {

        model.addAttribute("products",
                productService.getAllProducts());

        return "admin";
    }

    @GetMapping("/admin/products/add")
    public String addProductForm(Model model) {

        model.addAttribute("product", new Product());

        return "add-product";
    }

    @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute Product product) {

        productService.saveProduct(product);

        return "redirect:/admin";
    }

    @GetMapping("/admin/products/edit/{id}")
    public String editProduct(
            @PathVariable Long id,
            Model model) {

        Product product = productService
                .getProductById(id)
                .orElse(null);

        if (product == null) {
            return "redirect:/admin";
        }

        model.addAttribute("product", product);

        return "edit-product";
    }

    @PostMapping("/admin/products/edit/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @ModelAttribute Product product) {

        product.setId(id);

        productService.saveProduct(product);

        return "redirect:/admin";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return "redirect:/admin";
    }
}