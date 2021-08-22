package com.tbarauskas.parkingrestapi.dto.parking.fine;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ParkingFineRequestUpdateDTO {

    @NotNull
    @ApiModelProperty(notes = "Parking fine ID", required = true, name = "id", value = "1", example = "1")
    private Long id;

    @NotNull
//    TODO fix changed from User -> String
//    TODO new User as value needed?
    @ApiModelProperty(notes = "Parking fine's owner", required = true, name = "user", value = "user")
    private Long userId;

    @NotBlank
    private String parkingCity;

    @NotBlank
    private String parkingZone;

    @NotBlank
    private String recordStatus;
}
