package com.inventory.inventory.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockAdjustmentRequest {

    @NotNull(message = "quantity wajib diisi")
    @Min(value = 1, message = "quantity minimal 1")
    private Integer quantity;

    // Menggantikan @NoArgsConstructor
    public StockAdjustmentRequest() {
    }

    // Menggantikan @AllArgsConstructor
    public StockAdjustmentRequest(Integer quantity) {
        this.quantity = quantity;
    }
}