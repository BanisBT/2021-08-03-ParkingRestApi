package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.excepsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingCityRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingCityService {

    private final ParkingCityRepository cityRepository;

    public ParkingCityService(ParkingCityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public ParkingCity getCity(String cityName) {
        return cityRepository.getParkingCityByCityName(cityName).
                orElseThrow(() -> new AppParametersInDateBaseNotFoundException(cityName));
    }
}
