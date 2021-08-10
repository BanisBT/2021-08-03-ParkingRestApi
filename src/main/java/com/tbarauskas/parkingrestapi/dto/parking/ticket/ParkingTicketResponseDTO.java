package com.tbarauskas.parkingrestapi.dto.parking.ticket;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ParkingTicketResponseDTO {

    private Long id;

    private User user;

    private ParkingCity parkingCity;

    private ParkingZone parkingZone;

    private ParkingRecordStatus recordStatus;

    private LocalDateTime parkingBegan;

    private LocalDateTime parkingEnd;

    private BigDecimal ticketAmount;

    private LocalDateTime ticketDateTime;

    private LocalDateTime created;

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