package com.ecommerce.shipment.controller;

import com.ecommerce.shipment.dto.ShipmentDetailResponse;
import com.ecommerce.shipment.model.Shipment;
import com.ecommerce.shipment.service.ShipmentService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
        return new ResponseEntity<>(shipmentService.createShipment(shipment), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getById(@PathVariable String id) {
        return shipmentService.getShipmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Shipment> getByOrderId(@PathVariable String orderId) {
        return shipmentService.getShipmentByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/detail/{orderId}")
    public ResponseEntity<ShipmentDetailResponse> getDetailByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(shipmentService.getShipmentDetailWithOrder(orderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipment> updateShipment(@PathVariable String id, @RequestBody Shipment shipmentDetails) {
        return ResponseEntity.ok(shipmentService.updateShipment(id, shipmentDetails));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Shipment> updateStatus(@PathVariable String id, @RequestParam String status) {
        return ResponseEntity.ok(shipmentService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteShipment(@PathVariable String id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.ok(Map.of("deleted", Boolean.TRUE));
    }
}