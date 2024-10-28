package com.example.subproject.service;

import com.example.subproject.entity.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public Payment processPayment(Double amount, String method) {
        // Simulate PayPal payment processing
        Payment payment = new Payment();
        payment.setMethod(method);
        payment.setAmount(amount);
        payment.setStatus("Completed"); // Simulate successful payment
        return payment;
    }
}
