package com.product.product.dto;


import java.time.Instant;
import java.util.Objects;

/**
 * Wrapper response seragam untuk semua endpoint product-service,
 * supaya kontrak response konsisten dan mudah dikonsumsi service lain
 * (inventory-service, order-service, cart-service, dsb).
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Instant timestamp;

    // Menggantikan @NoArgsConstructor
    public ApiResponse() {
    }

    // Menggantikan @AllArgsConstructor
    public ApiResponse(boolean success, String message, T data, Instant timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, Instant.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, Instant.now());
    }

    // ===== Getter & Setter (menggantikan @Data) =====

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    // ===== equals & hashCode (menggantikan @Data) =====

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiResponse)) return false;
        ApiResponse<?> that = (ApiResponse<?>) o;
        return success == that.success
                && Objects.equals(message, that.message)
                && Objects.equals(data, that.data)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, data, timestamp);
    }

    // ===== toString (menggantikan @Data) =====

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}

