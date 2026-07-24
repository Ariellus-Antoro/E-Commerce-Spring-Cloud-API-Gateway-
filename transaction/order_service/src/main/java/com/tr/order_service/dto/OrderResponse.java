package com.tr.order_service.dto;

import java.math.BigDecimal;

public class OrderResponse {

    private String orderId;
    private BigDecimal totalAmount;
    private String status;

    public OrderResponse() {
    }

    public OrderResponse(String orderId, BigDecimal totalAmount, String status) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }
    
}
