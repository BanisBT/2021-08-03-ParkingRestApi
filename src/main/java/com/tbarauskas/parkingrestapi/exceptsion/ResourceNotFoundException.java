package com.tbarauskas.parkingrestapi.exceptsion;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{

    private final Long id;
}
