package com.tr.order_service.dto;

public class StockAdjustmentRequest {

    private Integer quantity;

    public StockAdjustmentRequest() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}