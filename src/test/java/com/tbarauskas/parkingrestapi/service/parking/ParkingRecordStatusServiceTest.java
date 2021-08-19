package com.tbarauskas.parkingrestapi.service.parking;

import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.exceptsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingRecordStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.PAID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingRecordStatusServiceTest {

    @Mock
    private ParkingRecordStatusRepository statusRepository;

    @Mock
    private ParkingRecordStatus parkingStatus;

    @InjectMocks
    private ParkingRecordStatusService statusService;

    @Test
    void testGetStatus() {
        when(statusRepository.getParkingRecordStatusByParkingStatusName(PAID.name()))
                .thenReturn(Optional.of(parkingStatus));

        ParkingRecordStatus recordStatus = statusService.getStatus(PAID.name());

        assertEquals(parkingStatus, recordStatus);
    }

    @Test
    void testGetStatusThenNotExist() {
        when(statusRepository.getParkingRecordStatusByParkingStatusName(any(String.class))).thenReturn(Optional.empty());

        assertThrows(AppParametersInDateBaseNotFoundException.class,() -> statusService.getStatus("not exist name"));
    }
}
