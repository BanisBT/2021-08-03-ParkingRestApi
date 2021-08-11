package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.excepsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingTicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingTicketService {

    private final ParkingTicketRepository ticketRepository;

    public ParkingTicketService(ParkingTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
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
        return ticketRepository.save(parkingTicket);
    }

    public ParkingTicket updateTicket(Long id, ParkingTicket updateParkingTicket) {
        ParkingTicket parkingTicket = getTicket(updateParkingTicket.getId());

        if (parkingTicket != null) {
            updateParkingTicket.setCreated(parkingTicket.getCreated());
            return ticketRepository.save(updateParkingTicket);
        }
        return null;
    }

    public void deleteTicket(Long id) {
        if (getTicket(id) != null) {
            ticketRepository.deleteById(id);
        }
    }
}
