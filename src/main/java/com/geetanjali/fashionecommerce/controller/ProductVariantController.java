package com.geetanjali.fashionecommerce.controller;

import com.geetanjali.fashionecommerce.entity.Product;
import com.geetanjali.fashionecommerce.entity.ProductVariant;
import com.geetanjali.fashionecommerce.service.ProductService;
import com.geetanjali.fashionecommerce.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/variants")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String variants(Model model) {

        model.addAttribute(
                "variants",
                productVariantService.getAllVariants()
        );

        return "admin-variants";
    }

    @GetMapping("/add")
    public String addVariantForm(Model model) {

        model.addAttribute(
                "variant",
                new ProductVariant()
        );

        model.addAttribute(
                "products",
                productService.getAllProducts()
        );

        return "admin-variant-form";
    }

    @PostMapping("/save")
    public String saveVariant(
            @ModelAttribute ProductVariant variant,
            @RequestParam("productId") Long productId) {

        Product product = productService
                .getProductById(productId)
                .orElse(null);

        if (product == null) {
            return "redirect:/admin/variants";
        }

        variant.setProduct(product);

        productVariantService.saveVariant(variant);

        return "redirect:/admin/variants";
    }

    @GetMapping("/edit/{id}")
    public String editVariant(
            @PathVariable Long id,
            Model model) {

        ProductVariant variant = productVariantService
                .getVariantById(id)
                .orElse(null);

        if (variant == null) {
            return "redirect:/admin/variants";
        }

        model.addAttribute("variant", variant);

        model.addAttribute(
                "products",
                productService.getAllProducts()
        );

        return "admin-variant-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteVariant(
            @PathVariable Long id) {

        productVariantService.deleteVariant(id);

        return "redirect:/admin/variants";
    }
}