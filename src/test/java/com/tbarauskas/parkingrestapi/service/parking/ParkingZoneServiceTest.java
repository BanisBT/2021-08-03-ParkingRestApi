package com.tbarauskas.parkingrestapi.service.parking;

import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.exceptsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.exceptsion.InvalidArgumentException;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingZoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.tbarauskas.parkingrestapi.model.ParkingZoneName.VILNIUS_BLUE_ZONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingZoneServiceTest {

    @Mock
    private ParkingZoneRepository zoneRepository;

    @Mock
    private ParkingZone zone;

    @InjectMocks
    private ParkingZoneService zoneService;

    @Test
    void testGetZone() {
        when(zoneRepository.getParkingZoneByZoneName(VILNIUS_BLUE_ZONE.name())).thenReturn(Optional.of(zone));

        ParkingZone parkingZone = zoneService.getZone(VILNIUS_BLUE_ZONE.name());

        assertEquals(zone, parkingZone);
    }

    @Test
    void testGetZoneThenNotExist() {
        when(zoneRepository.getParkingZoneByZoneName(any(String.class))).thenReturn(Optional.empty());

        assertThrows(AppParametersInDateBaseNotFoundException.class, () -> zoneService.getZone("not exist name"));
    }

    @Test
    void testGetZoneById() {
        when(zoneRepository.getParkingZoneById(1L)).thenReturn(Optional.of(zone));

        ParkingZone parkingZone = zoneService.getZoneById(1L);

        assertEquals(zone, parkingZone);
    }

    @Test
    void testGetZoneByIdThenNotExist() {
        when(zoneRepository.getParkingZoneById(any(long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> zoneService.getZoneById(101L));
    }

    @Test
    void testSetZoneFine() {
        BigDecimal newFineAmount = BigDecimal.valueOf(10);
        when(zoneRepository.getParkingZoneById(1L)).thenReturn(Optional.of(zone));

        zoneService.setZoneFine(1L, newFineAmount);

        verify(zoneRepository, times(1)).getParkingZoneById(1L);
        verify(zoneRepository, times(1)).save(zone);
    }

    @Test
    void testSetZoneFineNull() {
        assertThrows(InvalidArgumentException.class, () -> zoneService.setZoneFine(1L, null));
        verify(zoneRepository, times(0)).getParkingZoneById(1L);
        verify(zoneRepository, times(0)).save(zone);
    }

    @Test
    void setZoneCostPerHour() {
    }
}
