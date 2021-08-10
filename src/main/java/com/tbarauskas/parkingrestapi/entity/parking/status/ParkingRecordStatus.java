package com.tbarauskas.parkingrestapi.entity.parking.status;

import com.tbarauskas.parkingrestapi.model.ParkingStatusName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ParkingRecordStatus {

    private Long id;

    private ParkingStatusName parkingStatus;

    private LocalDateTime created;

    private LocalDateTime updated;
}
