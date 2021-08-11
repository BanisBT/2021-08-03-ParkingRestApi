package com.tbarauskas.parkingrestapi.model;

import lombok.Data;

@Data
public class Error {

    private final Integer status;

    private final String massage;
}
