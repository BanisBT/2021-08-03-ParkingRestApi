package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingCityRepository extends JpaRepository<ParkingCity, Long> {

    Optional<ParkingCity> getParkingCityByCityName(String cityName);
}
