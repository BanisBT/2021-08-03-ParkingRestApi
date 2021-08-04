package com.tbarauskas.parkingrestapi.controllers;

import com.tbarauskas.parkingrestapi.entities.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entities.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entities.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entities.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entities.user.User;
import com.tbarauskas.parkingrestapi.entities.user.UserRole;
import com.tbarauskas.parkingrestapi.model.ParkingCityName;
import com.tbarauskas.parkingrestapi.model.ParkingStatusName;
import com.tbarauskas.parkingrestapi.model.ParkingZoneName;
import com.tbarauskas.parkingrestapi.model.UserRoleName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/fines")
public class ParkingFineController {

    private final LocalDateTime now = LocalDateTime.now();

    private final ParkingCity city = new ParkingCity(1L, ParkingCityName.VILNIUS, now, now);

    private final ParkingZone zone = new ParkingZone(1L, ParkingZoneName.VILNIUS_BLUE_ZONE, now, now);

    private final ParkingRecordStatus status = new ParkingRecordStatus(1L, ParkingStatusName.OPEN, now, now);

    private final UserRole regular = new UserRole(1L, UserRoleName.REGULAR, now, now);

    private final User user = new User(1L, "Banis", "Geras", "Tomas", "Barauskas",
            "XXX 999", BigDecimal.ZERO, regular, now, now);

    @GetMapping("{id}")
    public ParkingFine getFine(@PathVariable Long id) {
        return new ParkingFine(id, user, city, zone, status, now, now, now, BigDecimal.ZERO);
    }

    @GetMapping()
    public List<ParkingFine> getFines() {
        return Collections.singletonList(new ParkingFine(1L, user, city, zone, status, now, now, now, BigDecimal.ZERO));
    }

    public ParkingFine createFine(@RequestBody ParkingFine fine) {
        fine.setId(2L);
        log.debug("ParkingFine - {} has been successfully created", fine);
        return fine;
    }
}
