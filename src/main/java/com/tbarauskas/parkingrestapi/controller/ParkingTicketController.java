package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.ParkingStatusRequestDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketRequestCreateDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketRequestUpdateTDO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketResponseDTO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.service.UserService;
import com.tbarauskas.parkingrestapi.service.parking.ParkingTicketService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/tickets")
@Api(tags = "ParkingTicket controller")
public class ParkingTicketController {

    private final ParkingTicketService ticketService;

    private final UserService userService;

    public ParkingTicketController(ParkingTicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("/{ticketId}")
    public ParkingTicketResponseDTO getTicket(@PathVariable Long ticketId) {
        return new ParkingTicketResponseDTO(ticketService.getTicket(ticketId));
    }

    @GetMapping("/{id}/user")
    public User getTicketsUser(@PathVariable Long id) {
        return ticketService.getTicketsUser(id);
    }

    @GetMapping
    public List<ParkingTicketResponseDTO> getTickets(@RequestParam(required = false, value = "from")
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
                                                     @RequestParam(required = false, value = "to")
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return ticketService.getTickets(dateFrom, dateTo).stream()
                .map(ParkingTicketResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingTicketResponseDTO createTicket(@Valid @RequestBody ParkingTicketRequestCreateDTO ticketDTO,
                                                 @AuthenticationPrincipal User user) {
        User userFromDb = userService.getUser(user.getId());
        ParkingTicket parkingTicket = ticketService.createTicket(userFromDb, ticketDTO);
        log.debug("ParkingTicket - {} has been successfully created", parkingTicket);
        return new ParkingTicketResponseDTO(parkingTicket);
    }

    @PutMapping("/{id}")
    public ParkingTicketResponseDTO updateTicket(@PathVariable Long id,
                                                 @Valid @RequestBody ParkingTicketRequestUpdateTDO ticketRequestTDO) {
        ParkingTicket parkingTicket = ticketService.updateTicket(id, ticketRequestTDO);
        log.debug("ParkingTicket - {} has been successfully created", parkingTicket);
        return new ParkingTicketResponseDTO(parkingTicket);
    }

    @PatchMapping("/{id}/setStatus")
    public void setFineStatus(@PathVariable Long id, @Valid @RequestBody ParkingStatusRequestDTO ticketStatus) {
        log.debug("Parking ticket's - {} status was changed to - {}", ticketService.getTicket(id),
                ticketStatus.getStatusName());
        ticketService.setTicketsStatus(id, ticketStatus.getStatusName());
    }

    @PatchMapping("/pay")
    public void setTicketStatus(@AuthenticationPrincipal User user) {
        log.debug("User - {} try to pay for parking ticket - {}", user, ticketService.getUsersOpenTicket(user));
        userService.isEnoughMoneyToPayForTicket(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id) {
        log.debug("ParkingTicket - {} has been successfully deleted", ticketService.getTicket(id));
        ticketService.deleteTicket(id);
    }
}
