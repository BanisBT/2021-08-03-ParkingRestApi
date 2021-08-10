package com.tbarauskas.parkingrestapi.dto.parking.fine;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UpdateParkingFineRequestDTO {

    @NotNull
    private Long id;

    @NotBlank
    private User user;

    @NotBlank
    private ParkingCity parkingCity;

    @NotBlank
    private ParkingZone parkingZone;

    @NotBlank
    private LocalDateTime fineDateTime;

    @NotBlank
    private ParkingRecordStatus recordStatus;
}
