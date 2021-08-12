package com.tbarauskas.parkingrestapi.excepsion;

import lombok.Data;

@Data
public class RecordStatusNotFoundException extends RuntimeException{

    private final String name;
}
