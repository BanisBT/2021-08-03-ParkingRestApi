package com.tbarauskas.parkingrestapi.dto.parking.ticket;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateParkingTicketRequestTDO {

    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    @NotBlank
    private String parkingCity;

    @NotBlank
    private String parkingZone;

    @NotBlank
    private String recordStatus;
}
