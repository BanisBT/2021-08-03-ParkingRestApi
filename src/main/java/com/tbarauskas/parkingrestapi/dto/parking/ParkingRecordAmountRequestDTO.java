package com.tbarauskas.parkingrestapi.dto.parking;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ParkingRecordAmountRequestDTO {

    @NotNull
    private BigDecimal amount;
}
