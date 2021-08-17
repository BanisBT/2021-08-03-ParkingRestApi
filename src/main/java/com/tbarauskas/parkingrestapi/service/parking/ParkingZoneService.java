package com.tbarauskas.parkingrestapi.service.parking;

import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.exceptsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingZoneRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public ParkingZone getZoneById(Long id) {
        return zoneRepository.getParkingZoneById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void setZoneFine(Long id, BigDecimal fineAmount) {
        ParkingZone zone = getZoneById(id);
        zone.setFine(fineAmount);
        zoneRepository.save(zone);
    }

    public void setZoneCostPerHour(Long id, BigDecimal costPerHour) {
        ParkingZone zone = getZoneById(id);
        zone.setCostPerHour(costPerHour);
        zoneRepository.save(zone);
    }
}
