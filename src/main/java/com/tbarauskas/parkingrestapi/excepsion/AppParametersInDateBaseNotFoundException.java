package com.tbarauskas.parkingrestapi.excepsion;

import lombok.Data;

@Data
public class AppParametersInDateBaseNotFoundException extends RuntimeException{

    private final String name;
}
