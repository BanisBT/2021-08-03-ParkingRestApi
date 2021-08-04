package com.tbarauskas.parkingrestapi.controllers;

import com.tbarauskas.parkingrestapi.entities.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entities.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entities.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entities.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entities.user.User;
import com.tbarauskas.parkingrestapi.entities.user.UserRole;
import com.tbarauskas.parkingrestapi.model.ParkingCityName;
import com.tbarauskas.parkingrestapi.model.ParkingStatusName;
import com.tbarauskas.parkingrestapi.model.ParkingZoneName;
import com.tbarauskas.parkingrestapi.model.UserRoleName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/tickets")
public class ParkingTicketController {

    private final LocalDateTime now = LocalDateTime.now();

    private final ParkingCity city = new ParkingCity(1L, ParkingCityName.VILNIUS, now, now);

    private final ParkingZone zone = new ParkingZone(1L, ParkingZoneName.VILNIUS_BLUE_ZONE, now, now);

    private final ParkingRecordStatus status = new ParkingRecordStatus(1L, ParkingStatusName.OPEN, now, now);

    private final UserRole regular = new UserRole(1L, UserRoleName.REGULAR, now, now);

    private final User user = new User(1L, "Banis", "Geras", "Tomas", "Barauskas",
            "XXX 999", BigDecimal.ZERO, regular, now, now);

    @GetMapping("/{id}")
    public ParkingTicket getTicket(@PathVariable Long id) {
        return new ParkingTicket(1L, user, city, zone, status, now, now, now, now.plusMinutes(60L), BigDecimal.ZERO);
    }
}
