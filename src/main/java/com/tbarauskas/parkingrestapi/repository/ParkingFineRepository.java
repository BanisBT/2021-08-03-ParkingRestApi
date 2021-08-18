package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingFineRepository extends JpaRepository<ParkingFine,Long> {

    Optional<ParkingFine> getParkingFineById(Long id);

    List<ParkingFine> getParkingFinesByFineDateTimeAfter(LocalDateTime dateFrom);

    List<ParkingFine> getParkingFinesByFineDateTimeBefore(LocalDateTime dateTo);

    List<ParkingFine> getParkingFinesByFineDateTimeBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

    List<ParkingFine> getParkingFinesByUserAndRecordStatus(User user, ParkingRecordStatus status);
}
