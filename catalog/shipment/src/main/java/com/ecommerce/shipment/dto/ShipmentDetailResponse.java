package com.ecommerce.shipment.dto;

import com.ecommerce.shipment.model.Shipment;

public class ShipmentDetailResponse {
    private Shipment shipment;
    private OrderResponseDTO order;

    public ShipmentDetailResponse() {}

    public ShipmentDetailResponse(Shipment shipment, OrderResponseDTO order) {
        this.shipment = shipment;
        this.order = order;
    }

    public Shipment getShipment() { return shipment; }
    public void setShipment(Shipment shipment) { this.shipment = shipment; }

    public OrderResponseDTO getOrder() { return order; }
    public void setOrder(OrderResponseDTO order) { this.order = order; }
}