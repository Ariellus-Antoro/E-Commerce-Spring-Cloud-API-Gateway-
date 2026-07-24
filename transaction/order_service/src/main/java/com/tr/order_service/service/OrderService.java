package com.tr.order_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tr.order_service.dto.ApiResponse;
import com.tr.order_service.dto.CreateOrderRequest;
import com.tr.order_service.dto.InventoryResponse;
import com.tr.order_service.dto.OrderItemRequest;
import com.tr.order_service.dto.OrderResponse;
import com.tr.order_service.dto.ProductResponse;
import com.tr.order_service.dto.StockAdjustmentRequest;
import com.tr.order_service.model.Order;
import com.tr.order_service.model.OrderItem;
import com.tr.order_service.model.OrderStatus;
import com.tr.order_service.repository.OrderItemRepository;
import com.tr.order_service.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final WebClient webClient;

    public OrderService(OrderRepository orderRepo,
                        OrderItemRepository orderItemRepo,
                        WebClient.Builder builder) {

        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.webClient = builder.build();
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }


    public Order getOrderById(String id) {
        return orderRepo.findById(id).orElse(null);
    }

    public OrderResponse createOrder(CreateOrderRequest request) {

        System.out.println("===== CREATE ORDER =====");
    System.out.println("UserId = " + request.getUserId());
    System.out.println("Items = " + request.getItems());


        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        List<OrderItem> orderItems = new ArrayList<>();

        // Hitung total dan siapkan OrderItem
        for (OrderItemRequest itemRequest : request.getItems()) {

            ProductResponse product = getProduct(itemRequest.getProductId());
            InventoryResponse inventory =
        getInventory(itemRequest.getProductId());

if (inventory == null) {
    throw new RuntimeException("Inventory tidak ditemukan");
}

if (inventory.getAvailableStock() < itemRequest.getQuantity()) {
    throw new RuntimeException("Stok tidak mencukupi");
}

reserveStock(
        itemRequest.getProductId(),
        itemRequest.getQuantity());

            if (product == null) {
                throw new RuntimeException(
                        "Produk dengan ID " + itemRequest.getProductId() + " tidak ditemukan.");
            }

            BigDecimal unitPrice = product.getBasePrice();

            BigDecimal subtotal = unitPrice.multiply(
                    BigDecimal.valueOf(itemRequest.getQuantity()));

            total = total.add(subtotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemRequest.getProductId());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
        }

        // Simpan Order terlebih dahulu agar UUID terbentuk
        order.setTotalAmount(total);
        order = orderRepo.save(order);

        // Simpan semua OrderItem
        for (OrderItem item : orderItems) {
            item.setOrder(order);
            orderItemRepo.save(item);
        }

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getOrderStatus().name());
    }

    public Order updateOrder(String id, Order order) {

        Order existing = orderRepo.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setUserId(order.getUserId());
        existing.setTotalAmount(order.getTotalAmount());
        existing.setOrderStatus(order.getOrderStatus());
        existing.setCreatedAt(order.getCreatedAt());

        return orderRepo.save(existing);
    }

    public void deleteOrder(String id) {
        orderRepo.deleteById(id);
    }

    // WEBCLIENT 
    private ProductResponse getProduct(String productId) {

        ApiResponse<ProductResponse> response =
                webClient.get()
                        .uri("http://localhost:8101/api/products/{id}", productId)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<ProductResponse>>() {})
                        .block();

        if (response == null || response.getData() == null) {
            return null;
        }

        return response.getData();
    }

    private InventoryResponse getInventory(String productId) {

    ApiResponse<InventoryResponse> response =
            webClient.get()
                    .uri("http://localhost:8102/api/inventories/product/{id}", productId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<InventoryResponse>>() {})
                    .block();

    if (response == null) {
        return null;
    }

    return response.getData();
    }
    private void reserveStock(String productId, Integer quantity) {

    StockAdjustmentRequest request = new StockAdjustmentRequest();
    request.setQuantity(quantity);

    webClient.patch()
            .uri("http://localhost:8102/api/inventories/product/{id}/reserve", productId)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}