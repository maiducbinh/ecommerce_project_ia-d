package com.example.subproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Item {

    private String author;
    private String isbn;
    private String publisher;
    private int pages;
}

