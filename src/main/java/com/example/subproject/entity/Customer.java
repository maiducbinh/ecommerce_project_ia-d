package com.example.subproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    // Mối quan hệ nhiều-nhiều với Role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customers_roles",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    // One-to-Many relationship với Order
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders;

    // One-to-One relationship với Cart
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    // One-to-Many relationship với Comment
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    // One-to-Many relationship với Rating
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Rating> ratings;
}
