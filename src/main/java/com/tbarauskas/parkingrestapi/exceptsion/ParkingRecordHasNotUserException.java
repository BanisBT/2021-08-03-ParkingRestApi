package com.tbarauskas.parkingrestapi.exceptsion;

import lombok.Data;

@Data
public class ParkingRecordHasNotUserException extends RuntimeException{

    private final Long id;
}
