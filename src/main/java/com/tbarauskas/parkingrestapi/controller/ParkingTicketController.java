package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.ticket.CreateParkingTicketRequestDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketResponseDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.UpdateParkingTicketRequestTDO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.service.ParkingTicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ParkingTicketResponseDTO createTicket(@Valid @RequestBody CreateParkingTicketRequestDTO ticketRequestDTO) {
        ParkingTicket parkingTicket = ticketService.createTicket(new ParkingTicket(ticketRequestDTO));
        log.debug("ParkingTicket - {} has been successfully created", parkingTicket);
        return new ParkingTicketResponseDTO(parkingTicket);
    }

    @PutMapping("/{id}")
    public ParkingTicketResponseDTO updateTicket(@Valid @PathVariable Long id, @RequestBody UpdateParkingTicketRequestTDO ticketRequestTDO) {
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
