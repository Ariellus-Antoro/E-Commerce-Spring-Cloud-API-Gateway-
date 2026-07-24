package com.tr.order_service.repository;

import org.springframework.data.repository.CrudRepository;

import com.tr.order_service.model.OrderItem;

public interface OrderItemRepository extends CrudRepository <OrderItem, Integer> {

}
