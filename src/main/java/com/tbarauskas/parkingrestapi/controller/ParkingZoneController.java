package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.zone.ParkingZoneResponseDTO;
import com.tbarauskas.parkingrestapi.service.parking.ParkingZoneService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/zones")
@Api(tags = "ParkingZone controller")
public class ParkingZoneController {

    private final ParkingZoneService zoneService;

    public ParkingZoneController(ParkingZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping("/{id}")
    public ParkingZoneResponseDTO getZone(@PathVariable Long id) {
        return new ParkingZoneResponseDTO(zoneService.getZoneById(id));
    }

    @PatchMapping("/{id}/fineAmount/{amount}")
    public void setZoneFine(@PathVariable Long id, @PathVariable(name = "amount")BigDecimal fineAmount) {
        log.debug("Parking zone's - {} fine amount was changed to - {}", zoneService.getZoneById(id), fineAmount);
        zoneService.setZoneFine(id, fineAmount);
    }

    @PatchMapping("/{id}/costPerHour/{cost}")
    public void setZoneCostPerHour(@PathVariable Long id, @PathVariable(name = "cost") BigDecimal costPerHour) {
        log.debug("Parking zone's - {} cost per hour was changed to - {}", zoneService.getZoneById(id), costPerHour);
        zoneService.setZoneCostPerHour(id, costPerHour);
    }
}
