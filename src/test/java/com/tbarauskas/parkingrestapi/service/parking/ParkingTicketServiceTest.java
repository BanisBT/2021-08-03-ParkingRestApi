package com.tbarauskas.parkingrestapi.service.parking;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingTicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingTicketServiceTest {

    @Mock
    private ParkingTicket ticket;

    @Mock
    private ParkingTicketRepository ticketRepository;

    @InjectMocks
    private ParkingTicketService ticketService;

    @Test
    void testGetTicket() {
        when(ticketRepository.getParkingTicketById(1L)).thenReturn(Optional.of(ticket));

        ParkingTicket parkingTicket = ticketService.getTicket(1L);

        assertEquals(ticket, parkingTicket);
    }

    @Test
    void testGetTicketThenNotExist() {
        when(ticketRepository.getParkingTicketById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.getTicket(100L));
    }

    @Test
    void getTickets() {
    }

    @Test
    void createTicket() {
    }

    @Test
    void updateTicket() {
    }

    @Test
    void deleteTicket() {
    }

    @Test
    void getTicketsUser() {
    }

    @Test
    void setTicketsStatus() {
    }
}
