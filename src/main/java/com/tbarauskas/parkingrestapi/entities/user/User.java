package com.tbarauskas.parkingrestapi.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String carNumber;

    private BigDecimal balance;

    private UserRole role;

    private LocalDateTime created;

    private LocalDateTime updated;
}
