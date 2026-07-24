package com.inventory.inventory.service.impl;

import com.inventory.inventory.client.ProductServiceClient;
import com.inventory.inventory.dto.ApiResponse;
import com.inventory.inventory.dto.InventoryRequest;
import com.inventory.inventory.dto.InventoryResponse;
import com.inventory.inventory.dto.ProductDto;
import com.inventory.inventory.exception.InsufficientStockException;
import com.inventory.inventory.exception.ResourceNotFoundException;
import com.inventory.inventory.model.Inventory;
import com.inventory.inventory.repository.InventoryRepository;
import com.inventory.inventory.service.InventoryService;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductServiceClient productServiceClient;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                 ProductServiceClient productServiceClient) {
        this.inventoryRepository = inventoryRepository;
        this.productServiceClient = productServiceClient;
    }

    @Override
    public InventoryResponse createInventory(InventoryRequest request) {
        validateProductExists(request.getProductId());

        if (inventoryRepository.existsByProductId(request.getProductId())) {
            throw new IllegalStateException(
                    "Inventory untuk productId " + request.getProductId() + " sudah ada");
        }

        Inventory inventory = Inventory.builder()
                .productId(request.getProductId())
                .availableStock(request.getAvailableStock())
                .reservedStock(request.getReservedStock() != null ? request.getReservedStock() : 0)
                .lastUpdated(Instant.now())
                .build();

        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse getInventoryById(String id) {
        return mapToResponse(findByIdOrThrow(id));
    }

    @Override
    public InventoryResponse getInventoryByProductId(String productId) {
        return mapToResponse(findByProductIdOrThrow(productId));
    }

    @Override
    public List<InventoryResponse> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponse updateInventory(String id, InventoryRequest request) {
        Inventory inventory = findByIdOrThrow(id);
        inventory.setAvailableStock(request.getAvailableStock());
        if (request.getReservedStock() != null) {
            inventory.setReservedStock(request.getReservedStock());
        }
        inventory.setLastUpdated(Instant.now());
        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse reserveStock(String productId, int quantity) {
        Inventory inventory = findByProductIdOrThrow(productId);

        if (inventory.getAvailableStock() < quantity) {
            throw new InsufficientStockException(
                    "Stok tidak cukup untuk productId " + productId
                            + ". Tersedia: " + inventory.getAvailableStock() + ", diminta: " + quantity);
        }

        inventory.setAvailableStock(inventory.getAvailableStock() - quantity);
        inventory.setReservedStock(inventory.getReservedStock() + quantity);
        inventory.setLastUpdated(Instant.now());

        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse releaseStock(String productId, int quantity) {
        Inventory inventory = findByProductIdOrThrow(productId);

        int actualRelease = Math.min(quantity, inventory.getReservedStock());
        inventory.setReservedStock(inventory.getReservedStock() - actualRelease);
        inventory.setAvailableStock(inventory.getAvailableStock() + actualRelease);
        inventory.setLastUpdated(Instant.now());

        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse confirmStock(String productId, int quantity) {
        Inventory inventory = findByProductIdOrThrow(productId);

        if (inventory.getReservedStock() < quantity) {
            throw new InsufficientStockException(
                    "Reserved stock tidak cukup untuk konfirmasi productId " + productId);
        }

        inventory.setReservedStock(inventory.getReservedStock() - quantity);
        inventory.setLastUpdated(Instant.now());

        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse restock(String productId, int quantity) {
        Inventory inventory = findByProductIdOrThrow(productId);
        inventory.setAvailableStock(inventory.getAvailableStock() + quantity);
        inventory.setLastUpdated(Instant.now());
        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public void deleteInventory(String id) {
        Inventory inventory = findByIdOrThrow(id);
        inventoryRepository.delete(inventory);
    }

    @Override
    public ProductDto getEnrichedProductInfo(String productId) {
        try {
            ApiResponse<ProductDto> response = productServiceClient.getProductById(productId);
            if (response == null || response.getData() == null) {
                throw new ResourceNotFoundException("Produk tidak ditemukan di product-service: " + productId);
            }
            return response.getData();
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Produk tidak ditemukan di product-service: " + productId);
        }
    }

    private void validateProductExists(String productId) {
        try {
            ApiResponse<Boolean> response = productServiceClient.checkProductExists(productId);
            if (response == null || response.getData() == null || !response.getData()) {
                throw new ResourceNotFoundException(
                        "Produk dengan id " + productId + " tidak ditemukan di product-service");
            }
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException(
                    "Produk dengan id " + productId + " tidak ditemukan di product-service");
        }
    }

    private Inventory findByIdOrThrow(String id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory tidak ditemukan dengan id: " + id));
    }

    private Inventory findByProductIdOrThrow(String productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory tidak ditemukan untuk productId: " + productId));
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .availableStock(inventory.getAvailableStock())
                .reservedStock(inventory.getReservedStock())
                .lastUpdated(inventory.getLastUpdated())
                .build();
    }
}