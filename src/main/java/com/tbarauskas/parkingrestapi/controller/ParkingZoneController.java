package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.zone.ParkingZoneResponseDTO;
import com.tbarauskas.parkingrestapi.service.parking.ParkingZoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @ApiOperation(value = "Get parking zone", tags = "getZone", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "ParkingZone not found"),
    })
    @GetMapping("/{id}")
    public ParkingZoneResponseDTO getZone(@PathVariable Long id) {
        return new ParkingZoneResponseDTO(zoneService.getZoneById(id));
    }

    @ApiOperation(value = "Set parking zone fine amount", tags = "setZoneFine", httpMethod = "SET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "ParkingZone not found"),
    })
    @PatchMapping("/{id}/fineAmount/{amount}")
    public void setZoneFine(@PathVariable Long id, @PathVariable(name = "amount")BigDecimal fineAmount) {
        log.debug("Parking zone's - {} fine amount was changed to - {}", zoneService.getZoneById(id), fineAmount);
        zoneService.setZoneFine(id, fineAmount);
    }

    @ApiOperation(value = "Set parking zone cost per hour", tags = "setZoneCostPerHour", httpMethod = "SET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "ParkingZone not found"),
    })
    @PatchMapping("/{id}/costPerHour/{cost}")
    public void setZoneCostPerHour(@PathVariable Long id, @PathVariable(name = "cost") BigDecimal costPerHour) {
        log.debug("Parking zone's - {} cost per hour was changed to - {}", zoneService.getZoneById(id), costPerHour);
        zoneService.setZoneCostPerHour(id, costPerHour);
    }
}
