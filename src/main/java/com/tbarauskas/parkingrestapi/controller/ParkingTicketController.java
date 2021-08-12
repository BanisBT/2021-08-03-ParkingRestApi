package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.ticket.CreateParkingTicketRequestDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketResponseDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.UpdateParkingTicketRequestTDO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.service.ParkingTicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tickets")
public class ParkingTicketController {

    private final ParkingTicketService ticketService;

    public ParkingTicketController(ParkingTicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public ParkingTicket getTicket(@PathVariable Long id) {
        return ticketService.getTicket(id);
    }

    @GetMapping("/{id}/user")
    public User getTicketsUser(@PathVariable Long id) {
        return ticketService.getTicketsUser(id);
    }

    @GetMapping
    public List<ParkingTicket> getTickets(@RequestParam(required = false, value = "from")
                                          @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
                                          @RequestParam(required = false, value = "to")
                                          @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return ticketService.getTickets(dateFrom, dateTo);
    }

    @PostMapping("/{city}/{zone}")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingTicketResponseDTO createTicket(@PathVariable(name = "city") String cityName,
                                                 @PathVariable(name = "zone") String zoneName,
                                                 @Valid @RequestBody CreateParkingTicketRequestDTO ticketRequestDTO) {
        ParkingTicket parkingTicket = ticketService.createTicket(new ParkingTicket(ticketRequestDTO), cityName, zoneName);
        log.debug("ParkingTicket - {} has been successfully created", parkingTicket);
        return new ParkingTicketResponseDTO(parkingTicket);
    }

    @PutMapping("/{id}")
    public ParkingTicketResponseDTO updateTicket(@Valid @PathVariable Long id,
                                                 @RequestBody UpdateParkingTicketRequestTDO ticketRequestTDO) {
        ParkingTicket parkingTicket = ticketService.updateTicket(id, new ParkingTicket(ticketRequestTDO));
        log.debug("ParkingTicket - {} has been successfully created", parkingTicket);
        return new ParkingTicketResponseDTO(parkingTicket);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id) {
        log.debug("ParkingTicket - {} has been successfully deleted", ticketService.getTicket(id));
        ticketService.deleteTicket(id);
    }
}
