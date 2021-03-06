package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    Optional<ParkingTicket> getParkingTicketById(Long id);

    Optional<ParkingTicket> getParkingTicketByUserAndRecordStatus(User user, ParkingRecordStatus status);

    List<ParkingTicket> getParkingTicketsByParkingBeganAfter(LocalDateTime dateFrom);

    List<ParkingTicket> getParkingTicketsByParkingBeganBefore(LocalDateTime dateTo);

    List<ParkingTicket> getParkingTicketsByParkingBeganBetween(LocalDateTime dateFrom, LocalDateTime dateTo);
}
