package com.example.subproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment content is required")
    @Column(columnDefinition = "TEXT")
    private String content;

    // Many-to-One with Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Many-to-One with Item
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}

