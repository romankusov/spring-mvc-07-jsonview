package org.example.jsonview.controller;

import org.example.jsonview.enums.OrderStatus;
import org.example.jsonview.model.Order;
import org.example.jsonview.model.User;
import org.example.jsonview.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    public void testGetAllUsers_UserSummaryView() throws Exception {

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/users/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].orders").doesNotExist());

        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUserById_UserDetailsView() throws Exception {
        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.orders").exists());

        verify(userService).getUserById(1);
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.createUser("John Doe", "john.doe@example.com")).thenReturn(user);

        mockMvc.perform(post("/users/")
                        .param("name", "John Doe")
                        .param("email", "john.doe@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(userService).createUser("John Doe", "john.doe@example.com");
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(1, "john.doe@example.com")).thenReturn(user);

        mockMvc.perform(put("/users/update")
                        .param("userId", "1")
                        .param("email", "john.doe@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(userService).updateUser(1, "john.doe@example.com");
    }

    @Test
    public void testAddOrder() throws Exception {
        Order order = new Order();
        order.setId(1);
        order.setTotalPrice(new BigDecimal("100.00"));
        order.setStatus(OrderStatus.CREATED);

        when(userService.addOrder(1, new BigDecimal("100.00"))).thenReturn(order);

        mockMvc.perform(post("/users/addOrder")
                        .param("userId", "1")
                        .param("totalPrice", "100.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalPrice").value(100.00))
                .andExpect(jsonPath("$.status").value("CREATED"));

        verify(userService).addOrder(1, new BigDecimal("100.00"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).deleteUser(1);
    }
}