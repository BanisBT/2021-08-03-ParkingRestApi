package com.tbarauskas.parkingrestapi.controllers;

import com.tbarauskas.parkingrestapi.entities.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.services.ParkingFineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/fines")
public class ParkingFineController {

    private final ParkingFineService fineService;

    public ParkingFineController(ParkingFineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping("{id}")
    public ParkingFine getFine(@PathVariable Long id) {
        return fineService.getParkingFine(id);
    }

    @GetMapping()
    public List<ParkingFine> getFines() {
        return fineService.getFines();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingFine createFine(@RequestBody ParkingFine fine) {
        fine.setId(2L);
        log.debug("ParkingFine - {} has been successfully created", fine);
        return fine;
    }

    @PutMapping("/{id}")
    public ParkingFine updateFine(@PathVariable Long id, @RequestBody ParkingFine fine) {
        log.debug("ParkingFine - {} has been successfully updated", fine);
        return fine;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFine(@PathVariable Long id, @RequestBody ParkingFine fine) {
        log.debug("ParkingFine - {} has been successfully deleted", fine);
    }
}
