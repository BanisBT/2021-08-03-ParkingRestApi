package com.tbarauskas.parkingrestapi.exceptsion;

import lombok.Data;

@Data
public class ParkingRecordHasNoUserException extends RuntimeException{

    private final Long id;
}
