package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.UNPAID;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParkingRecordStatusRepositoryTest {

    @Autowired
    private ParkingRecordStatusRepository statusRepository;

    @Test
    void testGetParkingRecordStatusByParkingStatusName() {

        ParkingRecordStatus status = statusRepository.getParkingRecordStatusByParkingStatusName(UNPAID.toString()).
                orElse(null);

        assert status != null;
        assertEquals(3L, status.getId());
        assertEquals(UNPAID.toString(), status.getParkingStatusName());
    }
}
