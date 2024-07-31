package org.example.jsonview.service.impl;

import lombok.AllArgsConstructor;
import org.example.jsonview.exceptions.customexceptions.NotFoundException;
import org.example.jsonview.model.Order;
import org.example.jsonview.model.User;
import org.example.jsonview.repository.OrderRepository;
import org.example.jsonview.repository.UserRepository;
import org.example.jsonview.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order getOrderById(Integer id) {
        return null;
    }

    @Override
    public Order createOrder(Integer userId, BigDecimal totalPrice) {
        Order order = new Order();
        User user = Optional.of(userRepository.getReferenceById(userId))
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " Не найден"));
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(String name) {

    }

}
