package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.excepsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.model.ParkingStatusName;
import com.tbarauskas.parkingrestapi.repository.ParkingTicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingTicketService {

    private final ParkingTicketRepository ticketRepository;

    private final ParkingRecordStatusService statusService;

    public ParkingTicketService(ParkingTicketRepository ticketRepository, ParkingRecordStatusService statusService) {
        this.ticketRepository = ticketRepository;
        this.statusService = statusService;
    }

    public ParkingTicket getTicket(Long id) {
        return ticketRepository.getParkingTicketById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<ParkingTicket> getTickets(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom == null && dateTo == null) {
            return ticketRepository.findAll();
        } else if (dateFrom == null) {
            return ticketRepository.getParkingTicketsByParkingBeganBefore(dateTo);
        } else if (dateTo == null) {
            return ticketRepository.getParkingTicketsByParkingBeganAfter(dateFrom);
        }
        return ticketRepository.getParkingTicketsByParkingBeganBetween(dateFrom, dateTo);
    }

    public ParkingTicket createTicket(ParkingTicket parkingTicket) {
        parkingTicket.setRecordStatus(statusService.getStatus(ParkingStatusName.OPEN.toString()));
        return ticketRepository.save(parkingTicket);
    }

    public ParkingTicket updateTicket(Long id, ParkingTicket updateParkingTicket) {
        ParkingTicket parkingTicket = getTicket(updateParkingTicket.getId());
        updateParkingTicket.setCreated(parkingTicket.getCreated());
        return ticketRepository.save(updateParkingTicket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(getTicket(id).getId());
    }

    public User getTicketsUser(Long id) {
        return getTicket(id).getUser();
    }
}
