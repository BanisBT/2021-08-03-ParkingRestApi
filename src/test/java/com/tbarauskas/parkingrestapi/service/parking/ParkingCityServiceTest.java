package com.tbarauskas.parkingrestapi.service.parking;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.exceptsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingCityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.tbarauskas.parkingrestapi.model.ParkingCityName.VILNIUS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingCityServiceTest {

    @Mock
    private ParkingCityRepository cityRepository;

    @Mock
    private ParkingCity city;

    @InjectMocks
    private ParkingCityService cityService;

    @Test
    void testGetCity() {
        when(cityRepository.getParkingCityByCityName(VILNIUS.name())).thenReturn(Optional.of(city));

        ParkingCity response = cityService.getCity(VILNIUS.name());

        assertEquals(city, response);
    }

    @Test
    void testGetCityThenNotExist() {
        when(cityRepository.getParkingCityByCityName(any(String.class))).thenReturn(Optional.empty());

        assertThrows(AppParametersInDateBaseNotFoundException.class, () -> cityService.getCity("not exist name"));
    }
}
