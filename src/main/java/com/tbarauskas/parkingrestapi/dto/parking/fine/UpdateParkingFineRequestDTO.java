package com.tbarauskas.parkingrestapi.dto.parking.fine;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UpdateParkingFineRequestDTO {

    @NotNull
    @ApiModelProperty(notes = "Parking fine ID", required = true, name = "id", value = "1", example = "1")
    private Long id;

    @NotBlank
//    TODO new User as value needed?
    @ApiModelProperty(notes = "Parking fine's owner", required = true, name = "user", value = "user")
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
