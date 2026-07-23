package com.product.product.controller;


import com.product.product.dto.ApiResponse;
import com.product.product.dto.ProductRequest;
import com.product.product.dto.ProductResponse;
import com.product.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Produk berhasil dibuat", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean active) {
        List<ProductResponse> products = productService.getAllProducts(category, active);
        return ResponseEntity.ok(ApiResponse.success("Daftar produk berhasil diambil", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable String id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Produk ditemukan", response));
    }

    /**
     * Dipakai service lain (cart-service, order-service) untuk mengambil
     * beberapa produk sekaligus berdasarkan daftar id.
     * Contoh: GET /api/products/batch?ids=64f...,64e...
     */
    @GetMapping("/batch")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByIds(@RequestParam List<String> ids) {
        List<ProductResponse> products = productService.getProductsByIds(ids);
        return ResponseEntity.ok(ApiResponse.success("Produk berhasil diambil", products));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<ApiResponse<Boolean>> checkProductExists(@PathVariable String id) {
        boolean exists = productService.existsById(id);
        return ResponseEntity.ok(ApiResponse.success("Pengecekan selesai", exists));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable String id, @Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success("Produk berhasil diperbarui", response));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<ProductResponse>> activateProduct(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success("Produk diaktifkan", productService.setActiveStatus(id, true)));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<ProductResponse>> deactivateProduct(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success("Produk dinonaktifkan", productService.setActiveStatus(id, false)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Produk berhasil dihapus", null));
    }
}

