package com.tbarauskas.parkingrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineRequestCreateDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineResponseDTO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.model.Error;
import com.tbarauskas.parkingrestapi.repository.ParkingFineRepository;
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

import java.time.LocalDateTime;

import static com.tbarauskas.parkingrestapi.model.ParkingCityName.KAUNAS;
import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.PAID;
import static com.tbarauskas.parkingrestapi.model.ParkingZoneName.KAUNAS_RED_ZONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WithMockUser(roles = {"MANAGER", "REGULAR"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingFineControllerIntegrationTest {

    LocalDateTime now = LocalDateTime.now();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingFineRepository fineRepository;

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
    void testGetFineDoesNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/fines/{id}", 10L))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(404, error.getStatus());
        assertEquals("Resource with id 10 - was not found", error.getMassage());
    }

    @Test
    void getFinesUser() {
    }

    @Test
    void getFines() {
    }

    @Test
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
    void testSetFineStatus() throws Exception {
        mockMvc.perform(patch("/fines/2/setStatus/{status}", PAID.name()))
                .andExpect(status().isAccepted())
                .andReturn();

        ParkingFine fineUnpaid = fineRepository.getParkingFineById(2L).orElse(null);

        assert fineUnpaid != null;
        assertEquals(PAID.name(), fineUnpaid.getRecordStatus().getParkingStatusName());
    }

    @Test
    void testSetFineStatusNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch("/fines/2/setStatus/{status}", "Not exist name"))
                .andExpect(status().isInternalServerError())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(500, error.getStatus());
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
