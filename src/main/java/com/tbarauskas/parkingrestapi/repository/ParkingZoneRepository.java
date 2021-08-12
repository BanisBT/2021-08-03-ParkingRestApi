package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingZoneRepository extends JpaRepository<ParkingZone, Long> {

    Optional<ParkingZone> getParkingZoneByZoneName(String zoneName);
}
