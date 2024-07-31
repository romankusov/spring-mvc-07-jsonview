package org.example.jsonview.service.impl;

import lombok.AllArgsConstructor;
import org.example.jsonview.enums.OrderStatus;
import org.example.jsonview.exceptions.customexceptions.NotFoundException;
import org.example.jsonview.model.Order;
import org.example.jsonview.model.User;
import org.example.jsonview.repository.OrderRepository;
import org.example.jsonview.repository.UserRepository;
import org.example.jsonview.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " Не найден"));
    }

    @Override
    public User createUser(String name, String email) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        System.out.println("entity: " + user.getEmail());
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(Integer id, String email) {
        User user = Optional.of(userRepository.getReferenceById(id))
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " Не найден"));
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    @Override
    //@Transactional
    public Order addOrder(Integer userId, BigDecimal totalPrice) {
        Order order = new Order();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " Не найден"));
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.CREATED);

        // user.getOrders().add(order);
       // System.out.println("orders: ");
        order = orderRepository.save(order);
        //user = userRepository.save(user);
        //user.getOrders().stream().map(Order::getId).forEach(System.out::println);
        //UserDTO userDTO = mapToUser(user);
        return order;
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

//    private UserDTO mapToUser(User user) {
//        return UserDTO.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .email(user.getEmail())
//                .build();
//    }

//    private OrderDTO  mapToOrder(Order order, UserDTO userDTO) {
//
//        return OrderDTO.builder()
//                .id(order.getId())
//                .totalPrice(order.getTotalPrice())
//                .user(userDTO).
//                build();
//    }
}
