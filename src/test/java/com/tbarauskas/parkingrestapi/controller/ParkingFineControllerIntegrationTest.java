package com.tbarauskas.parkingrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.repository.ParkingFineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WithMockUser(roles = {"MANAGER", "REGULAR"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingFineControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingFineRepository fineRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetFine() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/fines/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();
        ParkingFine fine = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ParkingFine.class);

        assertEquals(1, fine.getId());
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
