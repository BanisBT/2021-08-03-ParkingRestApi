package com.tbarauskas.parkingrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.parkingrestapi.dto.parking.ParkingStatusRequestDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineRequestCreateDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineResponseDTO;
import com.tbarauskas.parkingrestapi.dto.user.UserResponseDTO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.model.Error;
import com.tbarauskas.parkingrestapi.repository.ParkingFineRepository;
import com.tbarauskas.parkingrestapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.tbarauskas.parkingrestapi.model.ParkingCityName.KAUNAS;
import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.PAID;
import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.UNPAID;
import static com.tbarauskas.parkingrestapi.model.ParkingZoneName.KAUNAS_RED_ZONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingFineControllerIntegrationTest {

    LocalDateTime now = LocalDateTime.now();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingFineRepository fineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails("Admin")
    void testGetFine() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/fines/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        ParkingFineResponseDTO fine = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ParkingFineResponseDTO.class);

        assertEquals(1, fine.getId());
    }

    @Test
    @WithUserDetails("Banis")
    void testGetFineThenOwnsIt() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/fines/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        ParkingFineResponseDTO fine = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ParkingFineResponseDTO.class);

        assertEquals(1, fine.getId());
    }

    @Test
    @WithUserDetails("Maxima")
    void testGetFineThenNotHisAndNotManager() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/fines/{id}", 1L))
                .andExpect(status().isForbidden())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                Error.class);

        assertEquals(403, error.getStatus());
    }

    @Test
    @WithUserDetails("Admin")
    void testGetFineDoesNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/fines/{id}", 10L))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(404, error.getStatus());
        assertEquals("Resource with id 10 - was not found", error.getMassage());
    }

    @Test
    @WithUserDetails("Admin")
    void testGetFinesUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/fines/{id}/user", 1L))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDTO user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                UserResponseDTO.class);

        assertEquals(1, user.getId());
    }

    @Test
    @WithUserDetails("Admin")
    void testGetFinesUserThenUserNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/fines/{id}/user", 5L))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                Error.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Parking record don't has user", error.getMassage());
    }

    @Test
    @WithUserDetails("Maxima")
    void testGetFinesUserNotManager() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/fines/{id}/user", 1L))
                .andExpect(status().isForbidden())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(403, error.getStatus());
    }

    @Test
    void getFines() {
    }

    @Test
    @WithUserDetails("Admin")
    void testCreateFine() throws Exception {
        ParkingFineRequestCreateDTO fineDTO = new ParkingFineRequestCreateDTO(1L, KAUNAS.name(), KAUNAS_RED_ZONE.name());

        MvcResult mvcResult = mockMvc.perform(post("/fines/create")
                .content(objectMapper.writeValueAsString(fineDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        ParkingFineResponseDTO fineResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ParkingFineResponseDTO.class);

        ParkingFine fine = fineRepository.getParkingFineById(fineResponse.getId()).orElse(null);

        assertEquals(fineResponse.getId(), fine.getId());
        assertEquals(fineDTO.getUserId(), fine.getUser().getId());
        assertEquals(fineDTO.getParkingCity(), fine.getParkingCity().getCityName());
        assertEquals(fineDTO.getParkingZone(), fine.getParkingZone().getZoneName());
        assertTrue(fine.getCreated().isAfter(now));
    }

    @Test
    @WithUserDetails("Admin")
    void testCreateFineBadCityName() throws Exception {
        ParkingFineRequestCreateDTO fineDTO = new ParkingFineRequestCreateDTO(1L, KAUNAS.name(), "Not exist name");

        MvcResult mvcResult = mockMvc.perform(post("/fines/create")
                .content(objectMapper.writeValueAsString(fineDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(500, error.getStatus());
    }

    @Test
    @WithUserDetails("Admin")
    void testCreateFineThenUserDontExist() throws Exception {
        ParkingFineRequestCreateDTO fineDTO = new ParkingFineRequestCreateDTO(100L, KAUNAS.name(), KAUNAS_RED_ZONE.name());

        MvcResult mvcResult = mockMvc.perform(post("/fines/create")
                .content(objectMapper.writeValueAsString(fineDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource with id 100 - was not found", error.getMassage());
    }

    @Test
    @WithUserDetails("Admin")
    void testSetFineStatus() throws Exception {
        ParkingStatusRequestDTO requestDTO = new ParkingStatusRequestDTO(PAID.name());

        mockMvc.perform(patch("/fines/{id}/setStatus", 2L)
                .content(objectMapper.writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ParkingFine fineUnpaid = fineRepository.getParkingFineById(2L).orElse(null);

        assert fineUnpaid != null;
        assertEquals(PAID.name(), fineUnpaid.getRecordStatus().getParkingStatusName());
    }

    @Test
    @WithUserDetails("Admin")
    void testSetFineStatusNotExist() throws Exception {
        ParkingStatusRequestDTO requestDTO = new ParkingStatusRequestDTO("Not exist name");

        MvcResult mvcResult = mockMvc.perform(patch("/fines/{id}/setStatus", 2L)
                .content(objectMapper.writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(500, error.getStatus());
    }

    @Test
    @WithUserDetails("Banis")
    void testSetFineStatusPayInsufficientFunds() throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch("/fines/{id}/pay", 2L))
                .andExpect(status().isBadRequest())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);
        ParkingFine fine = fineRepository.getParkingFineById(2L).orElse(null);
        User user = userRepository.getUserByUsername("Banis").orElse(null);

        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Insufficient funds, your balance after paying ticket would be -200.00", error.getMassage());
        assert fine != null;
        assertEquals(UNPAID.name(), fine.getRecordStatus().getParkingStatusName());
        assert user != null;
        assertEquals("100.00", user.getBalance().toString());
    }

    @Test
    @WithUserDetails("Banis")
    void testSetFineStatusPay() throws Exception {
        mockMvc.perform(patch("/fines/{id}/pay", 1L))
                .andExpect(status().isOk())
                .andReturn();

        ParkingFine fine = fineRepository.getParkingFineById(1L).orElse(null);
        User user = userRepository.getUserByUsername("Banis").orElse(null);

        assert fine != null;
        assertEquals(PAID.name(), fine.getRecordStatus().getParkingStatusName());
        assert user != null;
        assertEquals("0.00", user.getBalance().toString());
    }

    @Test
    @WithUserDetails("Maxima")
    void testSetFineStatusPayNotOwnerNotManager() throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch("/fines/{id}/pay", 1L))
                .andExpect(status().isForbidden())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                Error.class);

        assertEquals(403, error.getStatus());
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
