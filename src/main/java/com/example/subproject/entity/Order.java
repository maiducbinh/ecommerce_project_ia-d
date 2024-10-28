package com.example.subproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mối quan hệ với Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Mối quan hệ với Cart
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // Mối quan hệ với Payment
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    // Mối quan hệ với Shipment
    @OneToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;
}
