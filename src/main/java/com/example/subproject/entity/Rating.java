package com.example.subproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Minimum rating is 1")
    @Max(value = 5, message = "Maximum rating is 5")
    private Integer score; // e.g., 1 to 5

    // Many-to-One with Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Many-to-One with Item
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}

