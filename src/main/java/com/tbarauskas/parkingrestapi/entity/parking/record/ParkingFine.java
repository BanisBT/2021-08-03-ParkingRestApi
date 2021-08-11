package com.tbarauskas.parkingrestapi.entity.parking.record;

import com.tbarauskas.parkingrestapi.dto.parking.fine.CreateParkingFineRequestDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.UpdateParkingFineRequestDTO;
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
@Table(name = "parking_fine")
@AllArgsConstructor
@NoArgsConstructor
public class ParkingFine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private ParkingCity parkingCity;

    @Transient
    private ParkingZone parkingZone;

    @Transient
    private ParkingRecordStatus recordStatus;

    @Column(name = "fine_date_time")
    private LocalDateTime fineDateTime;

    @Column(name = "fine_amount")
    private BigDecimal fineAmount;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

    public ParkingFine(CreateParkingFineRequestDTO createFineDTO) {
        this.user = createFineDTO.getUser();
        this.parkingCity = createFineDTO.getParkingCity();
        this.parkingZone = createFineDTO.getParkingZone();
        this.fineDateTime = createFineDTO.getFineDateTime();
    }

    public ParkingFine(UpdateParkingFineRequestDTO updateFineDTO) {
        this.id = updateFineDTO.getId();
        this.user = updateFineDTO.getUser();
        this.parkingCity = updateFineDTO.getParkingCity();
        this.parkingZone = updateFineDTO.getParkingZone();
        this.fineDateTime = updateFineDTO.getFineDateTime();
        this.recordStatus = updateFineDTO.getRecordStatus();
    }
}
