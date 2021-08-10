package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.fine.CreateParkingFineRequestDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineResponseDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.UpdateParkingFineRequestDTO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.service.ParkingFineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return fineService.getFine(id);
    }

    @GetMapping()
    public List<ParkingFine> getFines() {
        return fineService.getFines();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingFineResponseDTO createFine(@Valid @RequestBody CreateParkingFineRequestDTO fineRequestDTO) {
        ParkingFine parkingFine = fineService.createFine(new ParkingFine(fineRequestDTO));
        log.debug("ParkingFine - {} has been successfully created", parkingFine);
        return new ParkingFineResponseDTO(parkingFine);
    }

    @PutMapping("/{id}")
    public ParkingFineResponseDTO updateFine(@Valid @PathVariable Long id, @RequestBody UpdateParkingFineRequestDTO updateFineDTO) {
        ParkingFine parkingFine = fineService.updateFine(id, new ParkingFine(updateFineDTO));
        log.debug("ParkingFine - {} has been successfully updated", parkingFine);
        return new ParkingFineResponseDTO(parkingFine);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFine(@PathVariable Long id, @RequestBody ParkingFine fine) {
        log.debug("ParkingFine - {} has been successfully deleted", fine);
    }
}
