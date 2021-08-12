package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingCityRepository extends JpaRepository<ParkingCity, Long> {

    Optional<ParkingCity> getParkingCityByCityName(String cityName);
}
