package com.product.product.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private List<String> categories;
    private BigDecimal basePrice;
    private Boolean active;
    private Instant createdAt;
    private Instant updateAt;
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public BigDecimal getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    } 
}
