package com.tbarauskas.parkingrestapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = {"MANAGER", "REGULAR"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingFineControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFine() {
    }

    @Test
    void getFinesUser() {
    }

    @Test
    void getFines() {
    }

    @Test
    void createFine() {
    }

    @Test
    void setFineStatus() {
    }

    @Test
    void setFineAmount() {
    }

    @Test
    void updateFine() {
    }

    @Test
    void deleteFine() {
    }
}
