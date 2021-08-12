package com.tbarauskas.parkingrestapi.excepsion;

import lombok.Data;

@Data
public class ParkingZoneNotFoundException extends RuntimeException{

    private final String name;
}
