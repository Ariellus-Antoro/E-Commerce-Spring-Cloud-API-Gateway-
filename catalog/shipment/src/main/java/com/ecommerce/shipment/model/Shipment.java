package com.ecommerce.shipment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shipments")
public class Shipment {
    @Id
    private String id;
    private String orderId; // Ref ke orders.id (SQL)
    private String courier;
    private Recipient recipient;
    private String status;

    public Shipment() {}

    public Shipment(String id, String orderId, String courier, Recipient recipient, String status) {
        this.id = id;
        this.orderId = orderId;
        this.courier = courier;
        this.recipient = recipient;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCourier() { return courier; }
    public void setCourier(String courier) { this.courier = courier; }

    public Recipient getRecipient() { return recipient; }
    public void setRecipient(Recipient recipient) { this.recipient = recipient; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}