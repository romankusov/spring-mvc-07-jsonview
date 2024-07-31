package org.example.jsonview.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.jsonview.model.Order;
import org.example.jsonview.model.User;
import org.example.jsonview.service.UserService;
import org.example.jsonview.views.UserDetails;
import org.example.jsonview.views.UserSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    @JsonView(UserSummary.class)
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @JsonView(UserDetails.class)
    public ResponseEntity<User> getUserById(@PathVariable @Valid Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestParam @Valid String name,
                                           @RequestParam String email) {
        return ResponseEntity.ok(userService.createUser(name, email));
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestParam Integer userId, @RequestParam String email) {
        return ResponseEntity.ok(userService.updateUser(userId, email));
    }

    @PostMapping("/addOrder")
    public ResponseEntity<Order> addOrder(@RequestParam Integer userId,
                                          @RequestParam BigDecimal totalPrice) {
        return ResponseEntity.ok(userService.addOrder(userId, totalPrice));

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @Valid Integer id) {
        userService.deleteUser(id);
    }
}
