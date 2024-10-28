package com.example.subproject.service;

import com.example.subproject.entity.*;
import com.example.subproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Customer customer, Shipment shipment, Payment payment, Cart cart) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setShipment(shipment);
        order.setPayment(payment);
        order.setCart(cart);
        return orderRepository.save(order);
    }

    // Additional methods if needed
}

