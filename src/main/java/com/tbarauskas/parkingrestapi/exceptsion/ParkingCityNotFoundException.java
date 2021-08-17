package com.tbarauskas.parkingrestapi.exceptsion;

import lombok.Data;

@Data
public class ParkingCityNotFoundException extends RuntimeException{

    private final String name;
}
