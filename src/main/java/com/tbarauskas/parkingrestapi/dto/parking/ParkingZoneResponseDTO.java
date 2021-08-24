package com.tbarauskas.parkingrestapi.dto.parking;

import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ParkingZoneResponseDTO {

    private String zoneName;

    private BigDecimal zoneFine;

    private BigDecimal costPerHour;

    public ParkingZoneResponseDTO(ParkingZone parkingZone) {
        this.zoneName = parkingZone.getZoneName();
        this.zoneFine = parkingZone.getFine();
        this.costPerHour = parkingZone.getCostPerHour();
    }
}
