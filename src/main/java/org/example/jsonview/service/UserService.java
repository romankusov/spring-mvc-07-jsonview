package org.example.jsonview.service;

import org.example.jsonview.model.Order;
import org.example.jsonview.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Integer id);
    User createUser(String name, String email);
    void deleteUser(Integer id);
    User updateUser(Integer id, String email);
    Order addOrder(Integer userId, BigDecimal totalPrice);
}
