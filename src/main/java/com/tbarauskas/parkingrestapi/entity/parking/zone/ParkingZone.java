package com.tbarauskas.parkingrestapi.entity.parking.zone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingZone {

    private Long id;

    private String zoneName;

    private LocalDateTime created;

    private LocalDateTime updated;
}
