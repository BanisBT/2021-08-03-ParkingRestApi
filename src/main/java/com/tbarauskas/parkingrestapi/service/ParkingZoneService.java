package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.excepsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingZoneRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingZoneService {

    private final ParkingZoneRepository zoneRepository;

    public ParkingZoneService(ParkingZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public ParkingZone getZone(String zoneName) {
        return zoneRepository.getParkingZoneByZoneName(zoneName).
                orElseThrow(() -> new AppParametersInDateBaseNotFoundException(zoneName));
    }
}
