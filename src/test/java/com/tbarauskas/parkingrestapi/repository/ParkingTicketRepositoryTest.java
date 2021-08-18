package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.OPEN;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParkingTicketRepositoryTest {

    private final LocalDateTime after = LocalDateTime.parse("2021-04-05T20:00:00.000");

    private final LocalDateTime before = LocalDateTime.parse("2021-04-09T08:00:00.000");

    @Autowired
    private ParkingTicketRepository ticketRepository;

    @Autowired
    private ParkingRecordStatusRepository statusRepository;

    @Test
    void getParkingTicketById() {
        ParkingTicket ticket = ticketRepository.getParkingTicketById(1L).orElse(null);

        assert ticket != null;
        assertEquals(1L, ticket.getId());
    }

    @Test
    void getParkingTicketsByParkingBeganAfter() {
        List<ParkingTicket> tickets = ticketRepository.getParkingTicketsByParkingBeganAfter(after);

        assertEquals(2, tickets.size());
        assertEquals(2L, tickets.get(0).getId());
    }

    @Test
    void getParkingTicketsByParkingBeganBefore() {
        List<ParkingTicket> tickets = ticketRepository.getParkingTicketsByParkingBeganBefore(before);

        assertEquals(2, tickets.size());
        assertEquals(1L, tickets.get(0).getId());
    }

    @Test
    void getParkingTicketsByParkingBeganBetween() {
        List<ParkingTicket> tickets = ticketRepository.getParkingTicketsByParkingBeganBetween(after, before);

        assertEquals(1, tickets.size());
        assertEquals(2L, tickets.get(0).getId());
    }

    @Test
    void getParkingTicketByRecordStatus() {
        ParkingRecordStatus open = statusRepository.getParkingRecordStatusByParkingStatusName(OPEN.name())
                .orElse(null);
        ParkingTicket ticket = ticketRepository.getParkingTicketByRecordStatus(open).orElse(null);

        assert ticket != null;
        assertEquals(1L, ticket.getId());
        assertEquals(open, ticket.getRecordStatus());
    }
}
