package com.example.subproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String method;
    private String status;

    // Mối quan hệ với Order (nếu cần)
    @OneToOne(mappedBy = "payment")
    private Order order;
}
