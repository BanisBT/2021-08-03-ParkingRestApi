package com.tbarauskas.parkingrestapi.dto.parking.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ParkingTicketResponseDTO {

    private Long id;

    private User user;

    private ParkingCity parkingCity;

    private ParkingZone parkingZone;

    private ParkingRecordStatus recordStatus;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime parkingBegan;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime parkingEnd;

    private BigDecimal ticketAmount;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime ticketDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updated;

    public ParkingTicketResponseDTO(ParkingTicket parkingTicket) {
        this.id = parkingTicket.getId();
        this.user = parkingTicket.getUser();
        this.parkingCity = parkingTicket.getParkingCity();
        this.parkingZone = parkingTicket.getParkingZone();
        this.recordStatus = parkingTicket.getRecordStatus();
        this.parkingBegan = parkingTicket.getParkingBegan();
        this.parkingEnd = parkingTicket.getParkingEnd();
        this.ticketAmount = parkingTicket.getTicketAmount();
        this.ticketDateTime = parkingTicket.getCreated();
        this.created = parkingTicket.getCreated();
        this.updated = parkingTicket.getUpdated();
    }
}
