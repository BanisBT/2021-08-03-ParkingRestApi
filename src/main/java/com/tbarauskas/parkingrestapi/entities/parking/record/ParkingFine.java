package com.tbarauskas.parkingrestapi.entities.parking.record;

import com.tbarauskas.parkingrestapi.entities.parking.record.ParkingRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table(name = "parking_fine")
public class ParkingFine extends ParkingRecord {

    @Column(name = "fine_date_time")
    private LocalDateTime fineDateTime;

    @Column(name = "fine_amount")
    private BigDecimal fineAmount;

    public ParkingFine() {
    }
}
