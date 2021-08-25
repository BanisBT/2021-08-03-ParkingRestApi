package com.tbarauskas.parkingrestapi.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingStatusRequestDTO {

    @NotBlank
    private String statusName;
}
