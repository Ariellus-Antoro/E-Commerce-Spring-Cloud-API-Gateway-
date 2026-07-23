package com.inventory.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequest {

    @NotBlank(message = "productId wajib diisi")
    private String productId;

    @NotNull(message = "availableStock wajib diisi")
    @Min(value = 0, message = "availableStock tidak boleh negatif")
    private Integer availableStock;

    /**
     * Opsional, default 0 jika tidak diisi.
     */
    @Min(value = 0, message = "reservedStock tidak boleh negatif")
    private Integer reservedStock;

    // Menggantikan @NoArgsConstructor
    public InventoryRequest() {
    }

    // Menggantikan @AllArgsConstructor
    public InventoryRequest(String productId, Integer availableStock, Integer reservedStock) {
        this.productId = productId;
        this.availableStock = availableStock;
        this.reservedStock = reservedStock;
    }
}
