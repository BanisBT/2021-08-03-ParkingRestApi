package com.tbarauskas.parkingrestapi.entity.parking.status;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "parking_record_status")
public class ParkingRecordStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parking_status")
    private String parkingStatusName;

    @Column(name = "created")
    @CreationTimestamp
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime created;

    @Column(name = "updated")
    @UpdateTimestamp
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updated;
}
