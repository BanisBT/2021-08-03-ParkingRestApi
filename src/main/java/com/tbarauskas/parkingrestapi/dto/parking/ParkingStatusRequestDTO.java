package com.tbarauskas.parkingrestapi.dto.parking;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ParkingStatusRequestDTO {

    @NotBlank
    private String statusName;
}
