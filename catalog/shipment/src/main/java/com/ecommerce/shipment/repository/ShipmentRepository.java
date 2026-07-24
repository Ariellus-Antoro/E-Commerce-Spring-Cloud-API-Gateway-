package com.ecommerce.shipment.repository;

import com.ecommerce.shipment.model.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ShipmentRepository extends MongoRepository<Shipment, String> {
    Optional<Shipment> findByOrderId(String orderId);
}