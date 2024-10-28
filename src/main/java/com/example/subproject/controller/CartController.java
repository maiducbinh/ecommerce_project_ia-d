package com.example.subproject.controller;

import com.example.subproject.entity.*;
import com.example.subproject.service.*;
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
    private CustomerService customerService;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public String viewCart(Model model, Authentication authentication){
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getCartByCustomerId(customer.getId());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long itemId, Authentication authentication){
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getCartByCustomerId(customer.getId());
        cartService.addItemToCart(cart.getId(), itemId);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long itemId, Authentication authentication){
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getCartByCustomerId(customer.getId());
        cartService.removeItemFromCart(cart.getId(), itemId);
        return "redirect:/cart";
    }

    // Additional cart-related endpoints can be added here
}
