package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.tbarauskas.parkingrestapi.model.ParkingZoneName.VILNIUS_BLUE_ZONE;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParkingZoneRepositoryTest {

    @Autowired
    private ParkingZoneRepository zoneRepository;

    @Test
    void testGetParkingZoneByZoneName() {
        ParkingZone zone = zoneRepository.getParkingZoneByZoneName(VILNIUS_BLUE_ZONE.name()).orElse(null);

        assert zone != null;
        assertEquals(1L, zone.getId());
        assertEquals(zoneRepository.getParkingZoneById(1L).get().getZoneName(),zone.getZoneName() );
    }

    @Test
    void testGetParkingZoneById() {
        ParkingZone zone = zoneRepository.getParkingZoneById(1L).orElse(null);

        assert zone != null;
        assertEquals(VILNIUS_BLUE_ZONE.name(), zone.getZoneName());
        assertEquals(zoneRepository.getParkingZoneByZoneName(VILNIUS_BLUE_ZONE.name()).get().getId(), zone.getId());
    }
}
