package com.tbarauskas.parkingrestapi.dto.parking.fine;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class CreateParkingFineRequestDTO {

    @NotEmpty
    private User user;

    @NotEmpty
    private ParkingCity parkingCity;

    @NotEmpty
    private ParkingZone parkingZone;

    @NotEmpty
    private LocalDateTime fineDateTime;
}
