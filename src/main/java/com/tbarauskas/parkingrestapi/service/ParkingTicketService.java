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

    public ParkingTicket createTicket(ParkingTicket parkingTicket) {
        return ticketRepository.save(parkingTicket);
    }

    public ParkingTicket updateTicket(Long id, ParkingTicket updateParkingTicket) {
        ParkingTicket parkingTicket = ticketRepository.getParkingTicketById(updateParkingTicket.getId());

        if (parkingTicket != null) {
            updateParkingTicket.setCreated(parkingTicket.getCreated());
            return ticketRepository.save(updateParkingTicket);
        }
        return null;
    }
}
