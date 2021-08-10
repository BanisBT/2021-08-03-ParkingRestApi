package com.tbarauskas.parkingrestapi.entity.parking.record;

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

    @Transient
    private User user;

    @Transient
    private ParkingCity parkingCity;

    @Transient
    private ParkingZone parkingZone;

    @Transient
    private ParkingRecordStatus recordStatus;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @Column(name = "fine_date_time")
    private LocalDateTime fineDateTime;

    @Column(name = "fine_amount")
    private BigDecimal fineAmount;

}
