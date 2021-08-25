package com.tbarauskas.parkingrestapi.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRecordAmountRequestDTO {

    @NotNull
    private BigDecimal amount;
}
