package com.tbarauskas.parkingrestapi.repositories;

import com.tbarauskas.parkingrestapi.entities.parking.record.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    ParkingTicket getParkingTicketBy(Long id);
}
