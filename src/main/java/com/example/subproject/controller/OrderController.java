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

    // Hiển thị trang giỏ hàng
    @GetMapping("/cart")
    public String viewCart(Authentication authentication, Model model){
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getCartByCustomerId(customer.getId());
        model.addAttribute("cart", cart);
        return "view_cart";
    }

    // Hiển thị trang thanh toán
    @GetMapping("/checkout")
    public String showCheckoutPage(Authentication authentication, Model model){
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getCartByCustomerId(customer.getId());

        if(cart.getItems().isEmpty()){
            model.addAttribute("error", "Giỏ hàng của bạn đang trống!");
            return "view_cart";
        }

        // Tính tổng số tiền
        Double amount = cart.getItems().stream().mapToDouble(Item::getPrice).sum();
        model.addAttribute("totalAmount", amount);
        model.addAttribute("cart", cart);
        return "checkout";
    }

    // Xử lý thanh toán
    @PostMapping("/checkout")
    public String checkout(@RequestParam String paymentMethod,
                           @RequestParam String shipmentMethod,
                           Authentication authentication,
                           Model model){
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getCartByCustomerId(customer.getId());

        if(cart.getItems().isEmpty()){
            model.addAttribute("error", "Giỏ hàng của bạn đang trống!");
            return "view_cart";
        }

        // Tính tổng số tiền
        Double amount = cart.getItems().stream().mapToDouble(Item::getPrice).sum();

        // Xử lý thanh toán
        Payment payment = paymentService.processPayment(amount, paymentMethod);
        if(payment == null || !payment.getStatus().equalsIgnoreCase("Completed")){
            model.addAttribute("error", "Thanh toán không thành công. Vui lòng thử lại.");
            return "checkout";
        }

        // Tạo đơn vận chuyển
        Shipment shipment = shipmentService.createShipment(shipmentMethod);
        if(shipment == null){
            model.addAttribute("error", "Tạo đơn vận chuyển không thành công. Vui lòng thử lại.");
            return "checkout";
        }

        // Tạo đơn hàng
        Order order = orderService.createOrder(customer, shipment, payment, cart);

        if(order == null){
            model.addAttribute("error", "Tạo đơn hàng không thành công. Vui lòng thử lại.");
            return "checkout";
        }

        // Xóa giỏ hàng sau khi tạo đơn hàng thành công
        cartService.clearCart(cart.getId());

        model.addAttribute("order", order);
        return "order_confirmation";
    }

    // Thêm các endpoint liên quan đến đơn hàng khác ở đây
}
