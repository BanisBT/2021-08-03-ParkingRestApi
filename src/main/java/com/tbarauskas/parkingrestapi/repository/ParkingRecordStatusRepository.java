package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingRecordStatusRepository extends JpaRepository<ParkingRecordStatus, Long> {

    Optional<ParkingRecordStatus> getParkingRecordStatusByParkingStatusIgnoreCase(String parkingStatusName);
}
