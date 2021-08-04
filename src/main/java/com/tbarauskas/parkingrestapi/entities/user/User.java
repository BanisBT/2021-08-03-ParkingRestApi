package com.tbarauskas.parkingrestapi.entities.user;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String name;

    private String surname;
}
