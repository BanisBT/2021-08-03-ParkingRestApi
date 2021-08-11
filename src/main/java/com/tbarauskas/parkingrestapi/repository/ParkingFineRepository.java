package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParkingFineRepository extends JpaRepository<ParkingFine,Long> {

    ParkingFine getParkingFineById(Long id);

    List<ParkingFine> getParkingFinesByFineDateTimeAfter(LocalDateTime dateFrom);

    List<ParkingFine> getParkingFinesByFineDateTimeBefore(LocalDateTime dateTo);

    List<ParkingFine> getParkingFinesByFineDateTimeBetween(LocalDateTime dateFrom, LocalDateTime dateTo);
}
