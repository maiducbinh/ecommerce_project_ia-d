package com.example.subproject.controller;

import com.example.subproject.entity.*;
import com.example.subproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public String checkout(@RequestParam String paymentMethod, @RequestParam String shipmentMethod, Authentication authentication, Model model){
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getCartByCustomerId(customer.getId());

        if(cart.getItems().isEmpty()){
            model.addAttribute("error", "Your cart is empty!");
            return "view_cart";
        }

        // Calculate total amount
        Double amount = cart.getItems().stream().mapToDouble(Item::getPrice).sum();

        // Process Payment
        Payment payment = paymentService.processPayment(amount, paymentMethod);

        // Create Shipment
        Shipment shipment = shipmentService.createShipment(shipmentMethod);

        // Create Order
        Order order = orderService.createOrder(customer, shipment, payment, cart);

        // Clear the cart
        cartService.clearCart(cart.getId());

        model.addAttribute("order", order);
        return "order_confirmation";
    }

    // Additional order-related endpoints can be added here
}

