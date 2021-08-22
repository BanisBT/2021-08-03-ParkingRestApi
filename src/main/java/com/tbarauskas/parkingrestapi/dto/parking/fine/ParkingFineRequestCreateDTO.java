package com.tbarauskas.parkingrestapi.dto.parking.fine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingFineRequestCreateDTO {

    @NotNull
    private Long userId;

    @NotBlank
    private String parkingCity;

    @NotBlank
    private String parkingZone;
}
