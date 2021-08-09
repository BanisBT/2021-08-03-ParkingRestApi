package com.tbarauskas.parkingrestapi.entities.parking.record;

import com.tbarauskas.parkingrestapi.entities.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entities.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entities.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
public abstract class ParkingRecord {

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

    public ParkingRecord() {
    }
}
