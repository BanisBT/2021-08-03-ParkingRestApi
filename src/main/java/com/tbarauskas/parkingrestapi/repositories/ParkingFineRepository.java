package com.tbarauskas.parkingrestapi.repositories;

import com.tbarauskas.parkingrestapi.entities.parking.record.ParkingFine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParkingFineRepository extends JpaRepository<ParkingFine,Long> {

    ParkingFine getParkingFineById(Long id);
}
