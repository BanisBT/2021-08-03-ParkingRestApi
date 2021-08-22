package com.tbarauskas.parkingrestapi.dto.parking.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingTicketRequestCreateDTO {

    @NotBlank
    private String parkingCity;

    @NotBlank
    private String parkingZone;
}
