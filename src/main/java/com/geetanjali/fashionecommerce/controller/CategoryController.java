package com.geetanjali.fashionecommerce.controller;

import com.geetanjali.fashionecommerce.entity.Category;
import com.geetanjali.fashionecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model) {

        List<Category> categories =
                categoryService.getAllCategories();

        model.addAttribute("categories", categories);

        return "categories";
    }
}