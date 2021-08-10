package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.repository.ParkingTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingTicketService {

    private final ParkingTicketRepository ticketRepository;

    public ParkingTicketService(ParkingTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public ParkingTicket getTicket(Long id) {
        return ticketRepository.getParkingTicketById(id);
    }

    public List<ParkingTicket> getTickets() {
        return ticketRepository.findAll();
    }
}
