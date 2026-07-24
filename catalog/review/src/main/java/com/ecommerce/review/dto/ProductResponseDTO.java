package com.ecommerce.review.dto;

import java.math.BigDecimal;

public class ProductResponseDTO {
    private String id;
    private String name;
    private BigDecimal basePrice;

    public ProductResponseDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
}