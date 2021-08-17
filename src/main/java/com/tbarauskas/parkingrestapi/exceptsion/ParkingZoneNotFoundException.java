package com.tbarauskas.parkingrestapi.exceptsion;

import lombok.Data;

@Data
public class ParkingZoneNotFoundException extends RuntimeException{

    private final String name;
}
