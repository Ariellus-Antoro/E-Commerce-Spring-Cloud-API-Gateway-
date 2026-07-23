package com.product.product.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ProductRequest {

    @NotBlank(message = "name wajib diisi")
    private String name;

    private String description;

    @NotNull(message = "basePrice wajib diisi")
    @DecimalMin(value = "0.0", inclusive = true, message = "basePrice tidak boleh negatif")
    private BigDecimal basePrice;

    private List<String> categories;

    /**
     * Opsional. Jika null saat create, produk otomatis dianggap aktif (true).
     */
    private Boolean active;

    // Menggantikan @NoArgsConstructor
    public ProductRequest() {
    }

    // Menggantikan @AllArgsConstructor
    public ProductRequest(String name, String description, BigDecimal basePrice,
                           List<String> categories, Boolean active) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.categories = categories;
        this.active = active;
    }

    // ===== Getter & Setter (menggantikan @Data) =====

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    // ===== equals & hashCode (menggantikan @Data) =====

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductRequest)) return false;
        ProductRequest that = (ProductRequest) o;
        return Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(basePrice, that.basePrice)
                && Objects.equals(categories, that.categories)
                && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, basePrice, categories, active);
    }

    // ===== toString (menggantikan @Data) =====

    @Override
    public String toString() {
        return "ProductRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", basePrice=" + basePrice +
                ", categories=" + categories +
                ", active=" + active +
                '}';
    }
}
