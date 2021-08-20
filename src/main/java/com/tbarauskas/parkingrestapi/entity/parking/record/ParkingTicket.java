package com.tbarauskas.parkingrestapi.entity.parking.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.CreateParkingTicketRequestDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.UpdateParkingTicketRequestTDO;
import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "parking_ticket")
@AllArgsConstructor
@NoArgsConstructor
public class ParkingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "parking_city")
    private ParkingCity parkingCity;

    @ManyToOne
    @JoinColumn(name = "parking_zone")
    private ParkingZone parkingZone;

    @ManyToOne
    @JoinColumn(name = "parking_status")
    private ParkingRecordStatus recordStatus;

    @Column(name = "ticket_began")
    private LocalDateTime parkingBegan;

    @Column(name = "ticket_end")
    private LocalDateTime parkingEnd;

    @Column(name = "ticket_amount")
    private BigDecimal ticketAmount;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

    public ParkingTicket(CreateParkingTicketRequestDTO createTicketRequestDTO) {
        this.user = createTicketRequestDTO.getUser();
        this.parkingCity = createTicketRequestDTO.getParkingCity();
        this.parkingZone = createTicketRequestDTO.getParkingZone();
        this.parkingBegan = LocalDateTime.now();
    }

    public ParkingTicket(UpdateParkingTicketRequestTDO updateTicketRequestTDO) {
        this.id = updateTicketRequestTDO.getId();
        this.user = updateTicketRequestTDO.getUser();
        this.parkingCity = updateTicketRequestTDO.getParkingCity();
        this.parkingZone = updateTicketRequestTDO.getParkingZone();
        this.parkingBegan = updateTicketRequestTDO.getParkingBegan();
        this.parkingEnd = updateTicketRequestTDO.getParkingEnd();
    }
}
