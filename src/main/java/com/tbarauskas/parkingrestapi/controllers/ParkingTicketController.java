package com.tbarauskas.parkingrestapi.controllers;

import com.tbarauskas.parkingrestapi.entities.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.services.ParkingTicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<ParkingTicket> getTickets() {
        return ticketService.getTickets();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingTicket createTicket(@RequestBody ParkingTicket ticket) {
        ticket.setId(3L);
        log.debug("ParkingTicket - {} has been successfully created", ticket);
        return ticket;
    }

    @PutMapping("/{id}")
    public ParkingTicket updateTicket(@PathVariable Long id, @RequestBody ParkingTicket ticket) {
        log.debug("ParkingTicket - {} has been successfully created", ticket);
        return ticket;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id, @RequestBody ParkingTicket ticket) {
        log.debug("ParkingTicket - {} has been successfully deleted", ticket);
    }
}
