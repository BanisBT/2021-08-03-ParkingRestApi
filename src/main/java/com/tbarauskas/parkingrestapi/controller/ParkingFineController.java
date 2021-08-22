package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineRequestCreateDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineResponseDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineRequestUpdateDTO;
import com.tbarauskas.parkingrestapi.dto.user.UserResponseDTO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.service.UserService;
import com.tbarauskas.parkingrestapi.service.parking.ParkingFineService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/fines")
@Api(tags = "ParkingFine controller")
public class ParkingFineController {

    private final ParkingFineService fineService;

    private final UserService userService;

    public ParkingFineController(ParkingFineService fineService, UserService userService) {
        this.fineService = fineService;
        this.userService = userService;
    }

    @ApiOperation(value = "Get parking fine", tags = "getFine", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "ParkingFine not found"),
    })
    @GetMapping("{id}")
    public ParkingFineResponseDTO getFine(@PathVariable Long id) {
        return new ParkingFineResponseDTO(fineService.getFine(id));
    }

    @GetMapping("{id}/user")
    public UserResponseDTO getFinesUser(@PathVariable Long id) {
        return new UserResponseDTO(fineService.getFinesUser(id));
    }

    @GetMapping()
    public List<ParkingFineResponseDTO> getFines(@ApiParam(value = "Date and time from searching parking fine",
            example = "2021-04-09T11:00:00")
                                      @RequestParam(required = false, value = "from")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
                                      @RequestParam(required = false, value = "to")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return fineService.getFines(dateFrom, dateTo).stream()
                .map(ParkingFineResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}/setStatus/{status}")
    public void setFineStatus(@PathVariable Long id, @PathVariable(name = "status") String fineStatus) {
        log.debug("Parking fine's - {} status was changed to - {}", fineService.getFine(id), fineStatus);
        fineService.setFineStatus(id, fineStatus);
    }

//    TODO http status response?
    @PatchMapping("/{id}/setFineAmount/{amount}")
    public void setFineAmount(@PathVariable Long id, @PathVariable(name = "amount")BigDecimal fineAmount) {
        log.debug("Parking fine's - {} amount changed to - {}", fineService.getFine(id), fineAmount);
        fineService.setFineAmount(id, fineAmount);
    }

    @PutMapping("/{id}")
    public ParkingFineResponseDTO updateFine(@Valid @PathVariable Long id,
                                             @RequestBody ParkingFineRequestUpdateDTO updateFineDTO) {
        ParkingFine parkingFine = fineService.updateFine(id, updateFineDTO);
        log.debug("ParkingFine - {} has been successfully updated", parkingFine);
        return new ParkingFineResponseDTO(parkingFine);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingFineResponseDTO createFine(@Valid @RequestBody ParkingFineRequestCreateDTO fineRequest) {
        User userFromDb = userService.getUser(fineRequest.getUserId());
        ParkingFine parkingFine = fineService.createFine(userFromDb, fineRequest);
        log.debug("ParkingFine - {} has been successfully created", parkingFine);
        return new ParkingFineResponseDTO(parkingFine);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFine(@PathVariable Long id) {
        log.debug("ParkingFine - {} has been successfully deleted", fineService.getFine(id));
        fineService.deleteFine(id);
    }
}
