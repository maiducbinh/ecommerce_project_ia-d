package com.example.subproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "clothes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clothes extends Item {

    private String size;
    private String material;
    private String color;
}
