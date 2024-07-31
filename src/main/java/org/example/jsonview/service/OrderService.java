package org.example.jsonview.service;

import org.example.jsonview.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Integer id);
    Order createOrder(Integer userId, BigDecimal totalPrice);
    void deleteOrder(String name);
}
