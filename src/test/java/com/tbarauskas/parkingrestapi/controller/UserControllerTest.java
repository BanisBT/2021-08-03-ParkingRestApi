package com.tbarauskas.parkingrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.parkingrestapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(roles = {"MANAGER", "REGULAR"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetUser() {

    }

    @Test
    void getUsers() {
    }

    @Test
    void getUsersTickets() {
    }

    @Test
    void getUsersFines() {
    }

    @Test
    void getUsersUnpaidFines() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}
