package com.example.subproject.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {
    private Long id;

    @NotBlank(message = "Tên sản phẩm là bắt buộc")
    private String name;

    @NotNull(message = "Giá sản phẩm là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private Double price;

    @NotBlank(message = "Mô tả sản phẩm là bắt buộc")
    private String description;

    @NotBlank(message = "Loại sản phẩm là bắt buộc")
    private String dtype; // "Book" hoặc "Clothes"

    // Các trường bổ sung cho Book
    private String author;
    private String isbn;
    private String publisher;
    private Integer pages;

    // Các trường bổ sung cho Clothes
    private String size;
    private String material;
    private String color;
}

