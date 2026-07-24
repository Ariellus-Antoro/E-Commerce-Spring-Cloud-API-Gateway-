package com.ecommerce.shipment.service;

import com.ecommerce.shipment.client.OrderClient;
import com.ecommerce.shipment.dto.OrderResponseDTO;
import com.ecommerce.shipment.dto.ShipmentDetailResponse;
import com.ecommerce.shipment.model.Shipment;
import com.ecommerce.shipment.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderClient orderClient;

    public ShipmentService(ShipmentRepository shipmentRepository, OrderClient orderClient) {
        this.shipmentRepository = shipmentRepository;
        this.orderClient = orderClient;
    }

    public Shipment createShipment(Shipment shipment) {
        if (shipment.getStatus() == null) {
            shipment.setStatus("PENDING");
        }
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Optional<Shipment> getShipmentById(String id) {
    return shipmentRepository.findById(id);
}

    public Optional<Shipment> getShipmentByOrderId(String orderId) {
        return shipmentRepository.findByOrderId(orderId);
    }

    public ShipmentDetailResponse getShipmentDetailWithOrder(String orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        OrderResponseDTO order = null;
        try {
            order = orderClient.getOrderById(orderId);
        } catch (Exception e) { /* Fallback jika Order Service offline */ }

        return new ShipmentDetailResponse(shipment, order);
    }

    public Shipment updateShipment(String id, Shipment details) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        
        shipment.setCourier(details.getCourier());
        shipment.setRecipient(details.getRecipient());
        shipment.setStatus(details.getStatus());
        
        return shipmentRepository.save(shipment);
    }

    public Shipment updateStatus(String shipmentId, String status) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        shipment.setStatus(status);
        return shipmentRepository.save(shipment);
    }

    public void deleteShipment(String id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        shipmentRepository.delete(shipment);
    }
}