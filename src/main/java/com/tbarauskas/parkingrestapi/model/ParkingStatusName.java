package com.tbarauskas.parkingrestapi.model;

public enum ParkingStatusName {
    UNPAID("Unpaid"),
    OPEN("Open"),
    PAID("Paid");

    private String name;

    ParkingStatusName(String name) {
        this.name = name;
    }
}
