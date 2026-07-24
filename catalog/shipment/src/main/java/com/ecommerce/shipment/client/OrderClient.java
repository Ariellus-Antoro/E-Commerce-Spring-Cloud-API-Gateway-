package com.ecommerce.shipment.client;

import com.ecommerce.shipment.dto.OrderResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service") 
public interface OrderClient {

    @GetMapping("/api/orders/{id}")
    OrderResponseDTO getOrderById(@PathVariable("id") String id);
}