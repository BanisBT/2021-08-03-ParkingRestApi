package com.tbarauskas.parkingrestapi.exceptsion;

import lombok.Data;

@Data
public class AppParametersInDateBaseNotFoundException extends RuntimeException{

    private final String name;
}
