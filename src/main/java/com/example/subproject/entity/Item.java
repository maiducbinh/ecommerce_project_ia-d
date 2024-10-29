package com.example.subproject.entity;

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

    // Thêm trường type
    @Enumerated(EnumType.STRING)
    private ItemType type;  // Loại của item

    // Constructor có tham số để thiết lập type
    public Item(String name, Double price, String description, ItemType type) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
    }

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
