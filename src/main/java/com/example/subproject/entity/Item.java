package com.example.subproject.entity;

// src/main/java/com/example/ecommerce/entity/Item.java

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Double price;

    private String description;

    // Many-to-Many with Cart
    @ManyToMany(mappedBy = "items")
    private Set<Cart> carts;

    // One-to-Many relationship with Comment
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    // One-to-Many relationship with Rating
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Rating> ratings;
}
