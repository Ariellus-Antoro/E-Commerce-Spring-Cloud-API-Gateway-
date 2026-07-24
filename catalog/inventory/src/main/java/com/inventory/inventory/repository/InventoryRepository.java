package com.inventory.inventory.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventory.inventory.model.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String>{
     Optional<Inventory> findByProductId(String productId);

    boolean existsByProductId(String productId);
}
