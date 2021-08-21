package com.tbarauskas.parkingrestapi.dto.parking.fine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateParkingFineRequestDTO {

//    @NotBlank
//    private final User user;
    private Long userId;

//    @NotBlank
//    private final ParkingCity parkingCity;
    private final String parkingCityName;

//    @NotBlank
//    private final ParkingZone parkingZone;
    private final String parkingZoneName;

//    @NotEmpty
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime fineDateTime;
}
