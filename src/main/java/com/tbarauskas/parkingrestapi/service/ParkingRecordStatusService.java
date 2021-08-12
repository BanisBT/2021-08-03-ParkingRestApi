package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.excepsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingRecordStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ParkingRecordStatusService {

    private final ParkingRecordStatusRepository statusRepository;

    public ParkingRecordStatusService(ParkingRecordStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public ParkingRecordStatus getStatus(String statusName) {
        return statusRepository.getParkingRecordStatusByParkingStatusName(statusName).
                orElseThrow(() -> new AppParametersInDateBaseNotFoundException(statusName));
    }
}
