package com.tbarauskas.parkingrestapi.dto.parking.fine;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//TODO paklausit kuo susijusi si klase su getFine is controller?!?!?
@Data
@NoArgsConstructor
public class ParkingFineResponseDTO {

    private Long id;

    private String username;

    private String parkingCity;

    private String parkingZone;

    private String recordStatus;

    private LocalDateTime fineDateTime;

    private BigDecimal fineAmount;

    public ParkingFineResponseDTO(ParkingFine parkingFine) {
        this.id = parkingFine.getId();
        this.username = parkingFine.getUser().getUsername();
        this.parkingCity = parkingFine.getParkingCity().getCityName();
        this.parkingZone = parkingFine.getParkingZone().getZoneName();
        this.recordStatus = parkingFine.getRecordStatus().getParkingStatusName();
        this.fineDateTime = parkingFine.getFineDateTime();
        this.fineAmount = parkingFine.getFineAmount();
    }
}
