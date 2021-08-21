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

    public ParkingFine(User user, ParkingCity parkingCity, ParkingZone parkingZone, ParkingRecordStatus recordStatus,
                       LocalDateTime fineDateTime) {
        this.user = user;
        this.parkingCity = parkingCity;
        this.parkingZone = parkingZone;
        this.recordStatus = recordStatus;
        this.fineDateTime = fineDateTime;
    }
}
