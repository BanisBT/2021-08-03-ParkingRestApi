package com.tbarauskas.parkingrestapi.entity.parking.city;

import com.tbarauskas.parkingrestapi.model.ParkingCityName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ParkingCity {

    private Long id;

    private ParkingCityName parkingCity;

    private LocalDateTime created;

    private LocalDateTime updated;
}
