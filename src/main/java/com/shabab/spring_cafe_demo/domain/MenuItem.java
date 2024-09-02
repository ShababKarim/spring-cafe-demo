package com.shabab.spring_cafe_demo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class MenuItem {
    @Id
    private Long id;

    private String name;
    private List<String> ingredients;
    // Could implement optimistic locking with @Version column
    private Long quantity;
    private Double price;

    public MenuItem(String name, List<String> ingredients, Long quantity, Double price) {
        this.name = name;
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.price = price;
    }
}