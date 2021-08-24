package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.user.User;
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

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetParkingTicketById() {
        ParkingTicket ticket = ticketRepository.getParkingTicketById(1L).orElse(null);

        assert ticket != null;
        assertEquals(1L, ticket.getId());
    }

    @Test
    void testGetParkingTicketsByParkingBeganAfter() {
        List<ParkingTicket> tickets = ticketRepository.getParkingTicketsByParkingBeganAfter(after);

        assertEquals(2, tickets.size());
        assertEquals(2L, tickets.get(0).getId());
    }

    @Test
    void testGetParkingTicketsByParkingBeganBefore() {
        List<ParkingTicket> tickets = ticketRepository.getParkingTicketsByParkingBeganBefore(before);

        assertEquals(2, tickets.size());
        assertEquals(1L, tickets.get(0).getId());
    }

    @Test
    void testGetParkingTicketsByParkingBeganBetween() {
        List<ParkingTicket> tickets = ticketRepository.getParkingTicketsByParkingBeganBetween(after, before);

        assertEquals(1, tickets.size());
        assertEquals(2L, tickets.get(0).getId());
    }

    @Test
    void testGetParkingTicketByUserAndRecordStatus() {
        ParkingRecordStatus open = statusRepository.getParkingRecordStatusByParkingStatusName(OPEN.name())
                .orElse(null);
        User user = userRepository.getById(1L);

        ParkingTicket ticket = ticketRepository.getParkingTicketByUserAndRecordStatus(user, open).orElse(null);

        assert ticket != null;
        assertEquals(1L, ticket.getId());
    }
}
