package com.tbarauskas.parkingrestapi.dto.parking.fine;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateParkingFineResponseDTO {

    private Long id;

    private User user;

    private ParkingCity parkingCity;

    private ParkingZone parkingZone;

    private ParkingRecordStatus recordStatus;

    private LocalDateTime fineDateTime;

    private BigDecimal fineAmount;

    public CreateParkingFineResponseDTO(ParkingFine parkingFine) {
        this.id = parkingFine.getId();
        this.user = parkingFine.getUser();
        this.parkingCity = parkingFine.getParkingCity();
        this.parkingZone = parkingFine.getParkingZone();
        this.recordStatus = parkingFine.getRecordStatus();
        this.fineDateTime = parkingFine.getFineDateTime();
        this.fineAmount = parkingFine.getFineAmount();
    }
}
