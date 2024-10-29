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

    // Constructor để thiết lập loại
    public Book(String name, Double price, String description, String author, String isbn, String publisher, int pages) {
        super(name, price, description, ItemType.BOOK);
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.pages = pages;
    }
}
