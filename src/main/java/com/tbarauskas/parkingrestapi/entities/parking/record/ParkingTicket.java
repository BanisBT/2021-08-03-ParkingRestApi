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
@Table(name = "parking_ticket")
public class ParkingTicket extends ParkingRecord {

    @Column(name = "parking_began")
    private LocalDateTime parkingBegan;

    @Column(name = "parking_end")
    private LocalDateTime parkingEnd;

    @Column(name = "ticket_amount")
    private BigDecimal ticketAmount;

    public ParkingTicket() {
    }
}
