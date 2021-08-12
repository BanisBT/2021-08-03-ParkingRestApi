package com.tbarauskas.parkingrestapi.entity.parking.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingCity {

    private Long id;

    private String cityName;

    private LocalDateTime created;

    private LocalDateTime updated;
}
