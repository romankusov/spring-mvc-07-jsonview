package org.example.jsonview.controller;

import lombok.RequiredArgsConstructor;
import org.example.jsonview.model.Order;
import org.example.jsonview.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestParam Integer userId,
                                             @RequestParam BigDecimal totalPrice) {
        return null;
    }
}
