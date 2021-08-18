package com.tbarauskas.parkingrestapi.exceptsion;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import lombok.Data;

@Data
public class UserHasOpenTicketException extends RuntimeException{

    private final ParkingTicket openTicket;
}
