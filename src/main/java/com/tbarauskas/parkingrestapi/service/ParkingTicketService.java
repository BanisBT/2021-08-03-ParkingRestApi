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

    private final ParkingZoneService zoneService;

    private final ParkingCityService cityService;

    private final UserService userService;

    public ParkingTicketService(ParkingTicketRepository ticketRepository, ParkingRecordStatusService statusService,
                                ParkingZoneService zoneService, ParkingCityService cityService, UserService userService) {
        this.ticketRepository = ticketRepository;
        this.statusService = statusService;
        this.zoneService = zoneService;
        this.cityService = cityService;
        this.userService = userService;
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

    public ParkingTicket createTicket(ParkingTicket parkingTicket, String cityName, String zoneName) {
        parkingTicket.setUser(userService.getUser(1L));
        parkingTicket.setParkingCity(cityService.getCity(cityName));
        parkingTicket.setParkingZone(zoneService.getZone(zoneName));
        parkingTicket.setRecordStatus(statusService.getStatus(ParkingStatusName.OPEN.toString()));
        parkingTicket.setParkingBegan(LocalDateTime.now());
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
