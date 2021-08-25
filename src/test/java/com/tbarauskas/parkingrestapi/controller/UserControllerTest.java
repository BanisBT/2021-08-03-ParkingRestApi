package com.tbarauskas.parkingrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.parkingrestapi.dto.user.UserResponseDTO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.user.User;
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

import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.UNPAID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @WithUserDetails("Admin")
    void testGetUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDTO user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponseDTO.class);

        assertEquals(1, user.getId());
    }

    @Test
    @WithUserDetails("Banis")
    void testGetUserByHimSelf() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDTO user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponseDTO.class);

        assertEquals(1, user.getId());
    }

    @Test
    @WithUserDetails("Maxima")
    void testGetUserNotUserId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isForbidden())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(HttpStatus.FORBIDDEN.value(), error.getStatus());
        assertEquals("Access is forbidden", error.getMassage());
    }

    @Test
    @WithUserDetails("Admin")
    void testGetUserNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/{id}", 100L))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource with id 100 - was not found", error.getMassage());
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
