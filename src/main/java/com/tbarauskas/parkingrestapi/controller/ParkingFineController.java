package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.fine.CreateParkingFineRequestDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineResponseDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.UpdateParkingFineRequestDTO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.service.parking.ParkingFineService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/fines")
@Api(tags = "ParkingFine controller")
public class ParkingFineController {

    private final ParkingFineService fineService;

    public ParkingFineController(ParkingFineService fineService) {
        this.fineService = fineService;
    }

    @ApiOperation(value = "Get parking fine", tags = "getFine", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "ParkingFine not found"),
    })
    @GetMapping("{id}")
    public ParkingFine getFine(@PathVariable Long id) {
        return fineService.getFine(id);
    }

    @GetMapping("{id}/user")
    public User getFinesUser(@PathVariable Long id) {
        return fineService.getFinesUser(id);
    }

    @GetMapping()
    public List<ParkingFine> getFines(@ApiParam(value = "Date and time from searching parking fine", example = "2021-04-09T11:00:00")
                                      @RequestParam(required = false, value = "from")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
                                      @RequestParam(required = false, value = "to")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return fineService.getFines(dateFrom, dateTo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingFineResponseDTO createFine(@Valid @RequestBody CreateParkingFineRequestDTO fineRequestDTO) {
        ParkingFine parkingFine = fineService.createFine(new ParkingFine(fineRequestDTO));
        log.debug("ParkingFine - {} has been successfully created", parkingFine);
        return new ParkingFineResponseDTO(parkingFine);
    }

    @PatchMapping("/{id}/setStatus/{status}")
    public void setFineStatus(@PathVariable Long id, @PathVariable(name = "status") String fineStatus) {
        log.debug("Parking fine's - {} status was changed to - {}", fineService.getFine(id), fineStatus);
        fineService.setFineStatus(id, fineStatus);
    }

    @PutMapping("/{id}")
    public ParkingFineResponseDTO updateFine(@Valid @PathVariable Long id,
                                             @RequestBody UpdateParkingFineRequestDTO updateFineDTO) {
        ParkingFine parkingFine = fineService.updateFine(id, new ParkingFine(updateFineDTO));
        log.debug("ParkingFine - {} has been successfully updated", parkingFine);
        return new ParkingFineResponseDTO(parkingFine);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFine(@PathVariable Long id) {
        log.debug("ParkingFine - {} has been successfully deleted", fineService.getFine(id));
        fineService.deleteFine(id);
    }
}
