package com.inventory.inventory.service;


import com.inventory.inventory.dto.InventoryRequest;
import com.inventory.inventory.dto.InventoryResponse;
import com.inventory.inventory.dto.ProductDto;

import java.util.List;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    InventoryResponse getInventoryById(String id);

    InventoryResponse getInventoryByProductId(String productId);

    List<InventoryResponse> getAllInventories();

    InventoryResponse updateInventory(String id, InventoryRequest request);

    /**
     * Dipanggil saat order dibuat: memindahkan stok dari available -> reserved.
     */
    InventoryResponse reserveStock(String productId, int quantity);

    /**
     * Dipanggil saat order dibatalkan: memindahkan stok dari reserved -> available.
     */
    InventoryResponse releaseStock(String productId, int quantity);

    /**
     * Dipanggil saat order selesai/dikirim: mengurangi reserved secara permanen
     * (stok memang sudah keluar gudang, available tidak berubah lagi di sini).
     */
    InventoryResponse confirmStock(String productId, int quantity);

    /**
     * Menambah available_stock, misalnya saat restock dari supplier.
     */
    InventoryResponse restock(String productId, int quantity);

    void deleteInventory(String id);

    ProductDto getEnrichedProductInfo(String productId);
}

