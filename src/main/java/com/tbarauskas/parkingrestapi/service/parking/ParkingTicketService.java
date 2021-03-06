package com.tbarauskas.parkingrestapi.service.parking;

import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketRequestCreateDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketRequestUpdateTDO;
import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.exceptsion.UserHasOpenTicketException;
import com.tbarauskas.parkingrestapi.model.ParkingStatusName;
import com.tbarauskas.parkingrestapi.repository.ParkingTicketRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingTicketService {

    private final ParkingTicketRepository ticketRepository;

    private final ParkingRecordStatusService statusService;

    private final ParkingZoneService zoneService;

    private final ParkingCityService cityService;

    public ParkingTicketService(ParkingTicketRepository ticketRepository, ParkingRecordStatusService statusService,
                                ParkingZoneService zoneService, ParkingCityService cityService) {
        this.ticketRepository = ticketRepository;
        this.statusService = statusService;
        this.zoneService = zoneService;
        this.cityService = cityService;
    }

    public ParkingTicket getTicket(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ParkingTicket ticket = ticketRepository.getParkingTicketById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        if ( user.isManager() || ticket.getUser().getId().equals(user.getId()) ) {
            return ticket;
        }
        throw new AccessDeniedException("Access denied");
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

    public ParkingTicket createTicket(User user, ParkingTicketRequestCreateDTO fine) {
        ParkingTicket openTicket = getUsersOpenTicket(user);
        ParkingTicket ticket = new ParkingTicket();

        if (openTicket == null) {
            ticket.setUser(user);
            ticket.setParkingCity(cityService.getCity(fine.getParkingCity()));
            ticket.setParkingZone(zoneService.getZone(fine.getParkingZone()));
            ticket.setRecordStatus(statusService.getStatus(ParkingStatusName.OPEN.toString()));
            ticket.setParkingBegan(LocalDateTime.now());

            return ticketRepository.save(ticket);
        } else {
            throw new UserHasOpenTicketException(openTicket);
        }
    }

    public ParkingTicket updateTicket(Long id, ParkingTicketRequestUpdateTDO ticket) {
        ParkingRecordStatus status = statusService.getStatus(ticket.getRecordStatus());
        ParkingCity city = cityService.getCity(ticket.getParkingCity());
        ParkingZone zone = zoneService.getZone(ticket.getParkingZone());
        ParkingTicket parkingTicket = getTicket(ticket.getId());

        parkingTicket.setParkingZone(zone);
        parkingTicket.setParkingCity(city);
        parkingTicket.setRecordStatus(status);
        return ticketRepository.save(parkingTicket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(getTicket(id).getId());
    }

    public User getTicketsUser(Long id) {
        return getTicket(id).getUser();
    }

    public void setTicketsStatus(Long id, String statusName) {
        ParkingTicket ticket = getTicket(id);
        ticket.setRecordStatus(statusService.getStatus(statusName));
        ticketRepository.save(ticket);
    }

    public ParkingTicket getUsersOpenTicket(User user) {
        return ticketRepository.getParkingTicketByUserAndRecordStatus(user,
                statusService.getStatus(ParkingStatusName.OPEN.name())).orElse(null);
    }
}
