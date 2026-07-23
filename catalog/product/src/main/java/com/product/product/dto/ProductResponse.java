package com.product.product.dto;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private List<String> categories;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    // Menggantikan @NoArgsConstructor
    public ProductResponse() {
    }

    // Menggantikan @AllArgsConstructor
    public ProductResponse(String id, String name, String description, BigDecimal basePrice,
                            List<String> categories, boolean active, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.categories = categories;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Menggantikan static method builder() dari @Builder
    public static Builder builder() {
        return new Builder();
    }

    // ===== Getter & Setter (menggantikan @Data) =====

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean active() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ===== equals & hashCode (menggantikan @Data) =====

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductResponse)) return false;
        ProductResponse that = (ProductResponse) o;
        return active == that.active
                && Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(basePrice, that.basePrice)
                && Objects.equals(categories, that.categories)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, basePrice, categories, active, createdAt, updatedAt);
    }

    // ===== toString (menggantikan @Data) =====

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", basePrice=" + basePrice +
                ", categories=" + categories +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    // ===== Builder manual (menggantikan @Builder) =====

    public static class Builder {
        private String id;
        private String name;
        private String description;
        private BigDecimal basePrice;
        private List<String> categories;
        private boolean active;
        private Instant createdAt;
        private Instant updatedAt;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder basePrice(BigDecimal basePrice) {
            this.basePrice = basePrice;
            return this;
        }

        public Builder categories(List<String> categories) {
            this.categories = categories;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ProductResponse build() {
            return new ProductResponse(id, name, description, basePrice, categories, active, createdAt, updatedAt);
        }
    }
}