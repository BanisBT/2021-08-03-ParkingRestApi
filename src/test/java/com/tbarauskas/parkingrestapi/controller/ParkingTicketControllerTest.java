package com.tbarauskas.parkingrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketResponseDTO;
import com.tbarauskas.parkingrestapi.model.Error;
import com.tbarauskas.parkingrestapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @WithUserDetails("Admin")
    void testGetTicket() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/tickets/{id}", 2L))
                .andExpect(status().isOk())
                .andReturn();

        ParkingTicketResponseDTO ticket = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ParkingTicketResponseDTO.class);

        assertEquals(2, ticket.getId());
    }

    @Test
    @WithUserDetails("Banis")
    void testGetTicketThenOwnsTicket() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/tickets/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        ParkingTicketResponseDTO ticket = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ParkingTicketResponseDTO.class);

        assertEquals(1, ticket.getId());
    }

    @Test
    @WithUserDetails("Maxima")
    void testGetTicketThenNotHisAndNotManager() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/tickets/{id}", 1L))
                .andExpect(status().isForbidden())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(403, error.getStatus());
    }

    @Test
    @WithUserDetails("Maxima")
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
    @WithUserDetails("Banis")
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
