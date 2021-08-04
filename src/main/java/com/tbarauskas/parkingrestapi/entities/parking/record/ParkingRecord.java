package com.tbarauskas.parkingrestapi.entities.parking.record;

import com.tbarauskas.parkingrestapi.entities.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entities.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entities.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
public abstract class ParkingRecord {

    private Long id;

    private User user;

    private ParkingCity parkingCity;

    private ParkingZone parkingZone;

    private ParkingRecordStatus recordStatus;

    private LocalDateTime created;

    private LocalDateTime updated;
}
