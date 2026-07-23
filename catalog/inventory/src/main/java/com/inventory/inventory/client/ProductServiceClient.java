package com.inventory.inventory.client;


import com.inventory.inventory.dto.ApiResponse;
import com.inventory.inventory.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client yang menghubungi product-service melalui Eureka service discovery.
 * "name" harus sama persis dengan spring.application.name milik product-service.
 */
@FeignClient(name = "product")
public interface ProductServiceClient {

    @GetMapping("/api/products/{id}")
    ApiResponse<ProductDto> getProductById(@PathVariable("id") String id);

    @GetMapping("/api/products/exists/{id}")
    ApiResponse<Boolean> checkProductExists(@PathVariable("id") String id);
}

