package com.inventory.inventory.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
public class InventoryResponse {
    private String id;
    private String productId;
    private int availableStock;
    private int reservedStock;
    private Instant lastUpdated;

    // Menggantikan @NoArgsConstructor
    public InventoryResponse() {
    }

    // Menggantikan @AllArgsConstructor.
    // @Builder dipindah ke sini (bukan lagi di atas class) supaya Lombok
    // tahu pasti constructor mana yang harus dipakai untuk method .build().
    @Builder
    public InventoryResponse(String id, String productId, int availableStock,
                              int reservedStock, Instant lastUpdated) {
        this.id = id;
        this.productId = productId;
        this.availableStock = availableStock;
        this.reservedStock = reservedStock;
        this.lastUpdated = lastUpdated;
    }
}
