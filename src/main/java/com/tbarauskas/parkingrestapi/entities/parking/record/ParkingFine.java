package com.tbarauskas.parkingrestapi.entities.parking.record;

import com.tbarauskas.parkingrestapi.entities.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entities.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entities.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entities.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ParkingFine extends ParkingRecord{

    private LocalDateTime fineDateTime;

    private BigDecimal fineAmount;

    public ParkingFine(Long id, User user, ParkingCity parkingCity, ParkingZone parkingZone, ParkingRecordStatus recordStatus, LocalDateTime created, LocalDateTime updated, LocalDateTime fineDateTime, BigDecimal fineAmount) {
        super(id, user, parkingCity, parkingZone, recordStatus, created, updated);
        this.fineDateTime = fineDateTime;
        this.fineAmount = fineAmount;
    }
}
