package com.tr.order_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tr.order_service.dto.CreateOrderRequest;
import com.tr.order_service.dto.OrderResponse;
import com.tr.order_service.model.Order;
import com.tr.order_service.model.OrderStatus;
import com.tr.order_service.repository.OrderItemRepository;
import com.tr.order_service.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    public OrderService(OrderRepository orderRepo, OrderItemRepository orderItemRepo){
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }


    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }
    public Order getOrderById(String id) {
        return orderRepo.findById(id).orElse(null);
    }
    
    public OrderResponse createOrder(CreateOrderRequest request) {

    Order order = new Order();

        order.setUserId(request.getUserId());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
       
        BigDecimal total = BigDecimal.ZERO;

        
        // TODO
        // nanti loop item
        // panggil product service
        // panggil inventory service
        // hitung subtotal

        order.setTotalAmount(total);

        orderRepo.save(order);

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getOrderStatus().name());
        }

    public Order updateOrder(String id, Order order){

        Order existing = orderRepo.findById(id).orElse(null);

        if (existing == null){
            return null;
        }
        existing.setUserId(order.getUserId());
        existing.setTotalAmount(order.getTotalAmount());
        existing.setOrderStatus(order.getOrderStatus());
        existing.setCreatedAt(order.getCreatedAt());

        return orderRepo.save(existing);
    }
    
    public void deleteOrder(String id){
        orderRepo.deleteById(id);
    }
    
}
