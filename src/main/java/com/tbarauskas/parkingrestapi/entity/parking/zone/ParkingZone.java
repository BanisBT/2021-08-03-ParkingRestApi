package com.tbarauskas.parkingrestapi.entity.parking.zone;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_zone")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String zoneName;

    @Column(name = "fine")
    private BigDecimal fine;

    @Column(name = "cost_per_hour")
    private BigDecimal costPerHour;

    @CreationTimestamp
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime created;

    @UpdateTimestamp
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updated;
}
