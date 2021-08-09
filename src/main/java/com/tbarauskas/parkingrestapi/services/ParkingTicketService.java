package com.tbarauskas.parkingrestapi.services;

import com.tbarauskas.parkingrestapi.entities.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.repositories.ParkingTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingTicketService {

    private final ParkingTicketRepository ticketRepository;

    public ParkingTicketService(ParkingTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public ParkingTicket getTicket(Long id) {
        return ticketRepository.getParkingTicketBy(id);
    }

    public List<ParkingTicket> getTickets() {
        return ticketRepository.findAll();
    }
}
