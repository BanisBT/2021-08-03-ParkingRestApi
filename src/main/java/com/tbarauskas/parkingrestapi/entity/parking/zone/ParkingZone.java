package com.tbarauskas.parkingrestapi.entity.parking.zone;

import com.tbarauskas.parkingrestapi.model.ParkingZoneName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingZone {

    private Long id;

    private ParkingZoneName zoneName;

    private LocalDateTime created;

    private LocalDateTime updated;
}
