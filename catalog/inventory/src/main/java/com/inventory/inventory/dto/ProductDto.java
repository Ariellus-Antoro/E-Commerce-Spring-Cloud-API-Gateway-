package com.inventory.inventory.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private List<String> categories;
    private boolean active;
    public ProductDto() {
    }

    // Menggantikan @AllArgsConstructor
    public ProductDto(String id, String name, String description, BigDecimal basePrice,
                       List<String> categories, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.categories = categories;
        this.active = active;
    }
}