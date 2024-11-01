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

    // Constructor để thiết lập loại
    public Clothes(String name, Double price, String description, String size, String material, String color) {
        super(name, price, description, ItemType.CLOTHES);
        this.size = size;
        this.material = material;
        this.color = color;
    }
}
