package com.tbarauskas.parkingrestapi.excepsion;

import lombok.Data;

@Data
public class ParkingCityNotFoundException extends RuntimeException{

    private final String name;
}
