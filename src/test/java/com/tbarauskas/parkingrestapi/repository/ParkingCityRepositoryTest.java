package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.tbarauskas.parkingrestapi.model.ParkingCityName.VILNIUS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParkingCityRepositoryTest {

    @Autowired
    private ParkingCityRepository cityRepository;

    @Test
    public void testGetParkingCityByCityName() {
        ParkingCity parkingCity = cityRepository.getParkingCityByCityName(VILNIUS.name()).orElse(null);

        assert parkingCity != null;
        assertEquals(1, parkingCity.getId());
        assertEquals(VILNIUS.toString(), parkingCity.getCityName());
    }
}
