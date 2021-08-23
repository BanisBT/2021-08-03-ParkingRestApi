package com.tbarauskas.parkingrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketRequestCreateDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketResponseDTO;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.model.Error;
import com.tbarauskas.parkingrestapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static com.tbarauskas.parkingrestapi.model.ParkingCityName.VILNIUS;
import static com.tbarauskas.parkingrestapi.model.ParkingZoneName.VILNIUS_GREEN_ZONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = {"MANAGER", "REGULAR"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingTicketControllerTest {

    LocalDateTime now = LocalDateTime.now();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetTicket() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/tickets/{id}", 2L))
                .andExpect(status().isOk())
                .andReturn();

        ParkingTicketResponseDTO ticket = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ParkingTicketResponseDTO.class);

        assertEquals(2, ticket.getId());
    }

    @Test
    void testGetTicketNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/tickets/{id}", 200L))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                Error.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource with id 200 - was not found", error.getMassage());
    }

    @Test
    void getTicketsUser() {
    }

    @Test
    void getTickets() {
    }

    @Test
    void testCreateTicket() throws Exception {

    }

    @Test
    void updateTicket() {
    }

    @Test
    void changeFineStatus() {
    }

    @Test
    void deleteTicket() {
    }
}
