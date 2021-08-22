package com.tbarauskas.parkingrestapi.dto.parking.ticket;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ParkingTicketResponseDTO {

    private Long id;

    private String username;

    private String parkingCity;

    private String parkingZone;

    private String recordStatus;

    private LocalDateTime parkingBegan;

    private LocalDateTime parkingEnd;

    private BigDecimal ticketAmount;

    private LocalDateTime ticketDateTime;


    public ParkingTicketResponseDTO(ParkingTicket parkingTicket) {
        this.id = parkingTicket.getId();
        this.username = parkingTicket.getUser().getUsername();
        this.parkingCity = parkingTicket.getParkingCity().getCityName();
        this.parkingZone = parkingTicket.getParkingZone().getZoneName();
        this.recordStatus = parkingTicket.getRecordStatus().getParkingStatusName();
        this.parkingBegan = parkingTicket.getParkingBegan();
        this.parkingEnd = parkingTicket.getParkingEnd();
        this.ticketAmount = parkingTicket.getTicketAmount();
        this.ticketDateTime = parkingTicket.getCreated();
    }
}
