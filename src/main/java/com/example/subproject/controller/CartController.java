// src/main/java/com/example/subproject/controller/CartController.java

package com.example.subproject.controller;

import com.example.subproject.entity.Cart;
import com.example.subproject.entity.Item;
import com.example.subproject.entity.Customer;
import com.example.subproject.repository.CartRepository;
import com.example.subproject.repository.ItemRepository;
import com.example.subproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Xem Giỏ Hàng
    @GetMapping
    public String viewCart(Model model, Principal principal){
        String username = principal.getName();
        Customer customer = customerRepository.findByUsername(username).orElse(null);
        if(customer == null){
            return "redirect:/login";
        }

        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElse(new Cart());
        model.addAttribute("cart", cart);
        return "cart/view_cart";
    }

    // Thêm Sản Phẩm Vào Giỏ Hàng
    @PostMapping("/add/{itemId}")
    public String addToCart(@PathVariable Long itemId, Principal principal){
        String username = principal.getName();
        Customer customer = customerRepository.findByUsername(username).orElse(null);
        if(customer == null){
            return "redirect:/login";
        }

        Optional<Item> itemOpt = itemRepository.findById(itemId);
        if(!itemOpt.isPresent()){
            return "redirect:/items?error=ItemNotFound";
        }

        Item item = itemOpt.get();

        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElse(new Cart());
        cart.getItems().add(item);
        cart.setCustomer(customer);
        cartRepository.save(cart);

        return "redirect:/cart";
    }

    // Xóa Sản Phẩm Khỏi Giỏ Hàng
    @GetMapping("/remove/{itemId}")
    public String removeFromCart(@PathVariable Long itemId, Principal principal){
        String username = principal.getName();
        Customer customer = customerRepository.findByUsername(username).orElse(null);
        if(customer == null){
            return "redirect:/login";
        }

        Optional<Item> itemOpt = itemRepository.findById(itemId);
        if(!itemOpt.isPresent()){
            return "redirect:/cart?error=ItemNotFound";
        }

        Item item = itemOpt.get();

        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElse(null);
        if(cart != null){
            cart.getItems().remove(item);
            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }
}
