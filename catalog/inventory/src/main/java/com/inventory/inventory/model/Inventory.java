package com.inventory.inventory.model;


import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Builder;


@Builder
@Document(collection = "inventories")
public class Inventory {
    @Id
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String productId;
    private int availableStock;
    private int reservedStock;
    private Instant lastUpdated;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public int getAvailableStock() {
        return availableStock;
    }
    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
    public int getReservedStock() {
        return reservedStock;
    }
    public void setReservedStock(int reservedStock) {
        this.reservedStock = reservedStock;
    }
    public Instant getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}

