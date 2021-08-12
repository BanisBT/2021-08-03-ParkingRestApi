package com.tbarauskas.parkingrestapi.excepsion;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{

    private final Long id;
}
