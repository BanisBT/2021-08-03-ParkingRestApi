package com.tbarauskas.parkingrestapi.exceptsion;

import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NotEnoughMoneyToPayForTicketException extends RuntimeException{

    private final BigDecimal balance;

    private final User user;
}
