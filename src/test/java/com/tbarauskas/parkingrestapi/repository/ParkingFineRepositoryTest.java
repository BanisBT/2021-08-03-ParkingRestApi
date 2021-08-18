package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParkingFineRepositoryTest {

    private final LocalDateTime after = LocalDateTime.parse("2021-04-10T08:00:00.000");

    private final LocalDateTime before = LocalDateTime.parse("2021-04-11T08:00:00.000");

    @Autowired
    private ParkingFineRepository fineRepository;

    @Test
    void testGetParkingFineById() {
        ParkingFine fine = fineRepository.getParkingFineById(1L).orElse(null);

        assert fine != null;
        assertEquals(1L, fine.getId());
    }

    @Test
    void testGetParkingFinesByFineDateTimeAfter() {
        List<ParkingFine> fines = fineRepository.getParkingFinesByFineDateTimeAfter(after);

        assertEquals(3, fines.size());
    }

    @Test
    void testGetParkingFinesByFineDateTimeBefore() {
        List<ParkingFine> fines = fineRepository.getParkingFinesByFineDateTimeBefore(before);

        assertEquals(3, fines.size());
    }

    @Test
    void testGetParkingFinesByFineDateTimeBetween() {
        List<ParkingFine> fines = fineRepository.getParkingFinesByFineDateTimeBetween(after, before);

        assertEquals(1, fines.size());
    }
}
