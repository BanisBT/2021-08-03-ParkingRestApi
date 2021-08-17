package com.tbarauskas.parkingrestapi.exceptsion;

import lombok.Data;

@Data
public class UsernameAlreadyExistException extends RuntimeException{

    private final String username;
}
