package com.geetanjali.fashionecommerce.controller;

import com.geetanjali.fashionecommerce.entity.Brand;
import com.geetanjali.fashionecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/brands")
    public String brands(Model model) {

        List<Brand> brands = brandService.getAllBrands();

        model.addAttribute("brands", brands);

        return "brands";
    }
}