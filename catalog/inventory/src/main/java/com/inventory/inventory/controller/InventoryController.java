package com.inventory.inventory.controller;


import com.inventory.inventory.dto.*;
import com.inventory.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    // Menggantikan @RequiredArgsConstructor.
    // Spring tetap otomatis inject InventoryService lewat constructor ini,
    // karena hanya ada satu constructor di class ini.
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryResponse>> createInventory(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Inventory berhasil dibuat", inventoryService.createInventory(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAllInventories() {
        return ResponseEntity.ok(ApiResponse.success("Daftar inventory berhasil diambil", inventoryService.getAllInventories()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventoryById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success("Inventory ditemukan", inventoryService.getInventoryById(id)));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventoryByProductId(@PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success("Inventory ditemukan", inventoryService.getInventoryByProductId(productId)));
    }

    /**
     * Endpoint contoh yang memperkaya data inventory dengan info produk,
     * memanggil product-service secara real-time lewat Feign + Eureka.
     */
    @GetMapping("/product/{productId}/detail")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getInventoryDetail(@PathVariable String productId) {
        InventoryResponse inventory = inventoryService.getInventoryByProductId(productId);
        ProductDto product = inventoryService.getEnrichedProductInfo(productId);

        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("inventory", inventory);
        detail.put("product", product);

        return ResponseEntity.ok(ApiResponse.success("Detail inventory dan produk berhasil diambil", detail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryResponse>> updateInventory(
            @PathVariable String id, @Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Inventory berhasil diperbarui", inventoryService.updateInventory(id, request)));
    }

    @PatchMapping("/product/{productId}/reserve")
    public ResponseEntity<ApiResponse<InventoryResponse>> reserveStock(
            @PathVariable String productId, @Valid @RequestBody StockAdjustmentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Stok berhasil direservasi",
                inventoryService.reserveStock(productId, request.getQuantity())));
    }

    @PatchMapping("/product/{productId}/release")
    public ResponseEntity<ApiResponse<InventoryResponse>> releaseStock(
            @PathVariable String productId, @Valid @RequestBody StockAdjustmentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Stok berhasil dilepas kembali ke available",
                inventoryService.releaseStock(productId, request.getQuantity())));
    }

    @PatchMapping("/product/{productId}/confirm")
    public ResponseEntity<ApiResponse<InventoryResponse>> confirmStock(
            @PathVariable String productId, @Valid @RequestBody StockAdjustmentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Stok berhasil dikonfirmasi keluar",
                inventoryService.confirmStock(productId, request.getQuantity())));
    }

    @PatchMapping("/product/{productId}/restock")
    public ResponseEntity<ApiResponse<InventoryResponse>> restock(
            @PathVariable String productId, @Valid @RequestBody StockAdjustmentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Stok berhasil ditambahkan",
                inventoryService.restock(productId, request.getQuantity())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.ok(ApiResponse.success("Inventory berhasil dihapus", null));
    }
}
