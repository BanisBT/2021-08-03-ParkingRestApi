package com.tbarauskas.parkingrestapi.entities.parking.zone;

import com.tbarauskas.parkingrestapi.model.ParkingZoneName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ParkingZone {

    private Long id;

    private ParkingZoneName zoneName;

    private LocalDateTime created;

    private LocalDateTime updated;
}
