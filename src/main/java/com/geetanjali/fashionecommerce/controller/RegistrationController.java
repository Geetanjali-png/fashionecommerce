package com.geetanjali.fashionecommerce.controller;

import com.geetanjali.fashionecommerce.entity.User;
import com.geetanjali.fashionecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {

        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already registered.");
            return "register";
        }

        userService.registerUser(user);

        return "redirect:/login?registered";
    }
}