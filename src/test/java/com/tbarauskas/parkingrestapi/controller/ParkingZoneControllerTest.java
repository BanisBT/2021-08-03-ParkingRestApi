package com.tbarauskas.parkingrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbarauskas.parkingrestapi.dto.parking.ParkingRecordAmountRequestDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ParkingZoneResponseDTO;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.model.Error;
import com.tbarauskas.parkingrestapi.repository.ParkingZoneRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = {"MANAGER", "REGULAR"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingZoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ParkingZoneRepository zoneRepository;

    @Test
    void testGetZone() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/zones/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        ParkingZoneResponseDTO zone = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ParkingZoneResponseDTO.class);

        ParkingZone zoneDb = zoneRepository.getParkingZoneById(1L).orElse(null);

        assert zoneDb != null;
        assertEquals(zoneDb.getZoneName(), zone.getZoneName());
        assertEquals(zoneDb.getCostPerHour(), zone.getCostPerHour());
        assertEquals(zoneDb.getFine(), zone.getZoneFine());
    }

    @Test
    void testGetZoneThenNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/zones/{id}", 12))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource with id 12 - was not found", error.getMassage());
    }

    @Test
    @WithUserDetails("Admin")
    void testSetZoneFine() throws Exception {
        ParkingRecordAmountRequestDTO requestDTO = new ParkingRecordAmountRequestDTO(BigDecimal.TEN);

        mockMvc.perform(patch("/zones/{id}/fineAmount", 2L)
                .content(objectMapper.writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ParkingZone zone = zoneRepository.getParkingZoneById(2L).orElse(null);

        assert zone != null;
        assertEquals(BigDecimal.TEN.longValue(), zone.getFine().longValue());
    }

    @Test
    @WithUserDetails("Admin")
    void testSetZoneFineThenNotExist() throws Exception {
        ParkingRecordAmountRequestDTO requestDTO = new ParkingRecordAmountRequestDTO(BigDecimal.TEN);

        MvcResult mvcResult = mockMvc.perform(patch("/zones/{id}/fineAmount", 12L)
                .content(objectMapper.writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource with id 12 - was not found", error.getMassage());
    }

    @Test
    @WithUserDetails("Admin")
    void testSetZoneCostPerHour() throws Exception {
        ParkingRecordAmountRequestDTO requestDTO = new ParkingRecordAmountRequestDTO(BigDecimal.TEN);

        mockMvc.perform(patch("/zones/{id}/costPerHour", 2L)
                .content(objectMapper.writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ParkingZone zone = zoneRepository.getParkingZoneById(2L).orElse(null);

        assert zone != null;
        assertEquals(BigDecimal.TEN.longValue(), zone.getCostPerHour().longValue());
    }

    @Test
    @WithUserDetails("Admin")
    void testSetZoneCostPerHourThenNotExist() throws Exception {
        ParkingRecordAmountRequestDTO requestDTO = new ParkingRecordAmountRequestDTO(BigDecimal.TEN);

        MvcResult mvcResult = mockMvc.perform(patch("/zones/{id}/costPerHour", 12L)
                .content(objectMapper.writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Error error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Error.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals("Resource with id 12 - was not found", error.getMassage());
    }
}
