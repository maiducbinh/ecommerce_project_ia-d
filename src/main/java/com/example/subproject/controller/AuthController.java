package com.example.subproject.controller;

import com.example.subproject.entity.Cart;
import com.example.subproject.entity.Customer;
import com.example.subproject.service.CartService;
import com.example.subproject.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("customer") @Valid Customer customer, BindingResult result, Model model){
        if(result.hasErrors()){
            return "register";
        }
        if(customerService.findByUsername(customer.getUsername()).isPresent()){
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        if(customerService.findByEmail(customer.getEmail()).isPresent()){
            model.addAttribute("error", "Email already in use");
            return "register";
        }
        customerService.registerCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    // Additional authentication-related methods can be added here
}

