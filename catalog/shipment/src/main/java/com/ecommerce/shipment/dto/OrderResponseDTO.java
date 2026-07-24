package com.ecommerce.shipment.dto;

import java.math.BigDecimal;

public class OrderResponseDTO {
    private String id;
    private Integer userId;
    private BigDecimal totalAmount;
    private String orderStatus;

    public OrderResponseDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
}