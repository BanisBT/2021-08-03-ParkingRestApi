package com.tbarauskas.parkingrestapi.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserResponseDTO {

    private Long id;

    private String username;

    private String name;

    private String surname;

    private String carNumber;

    private BigDecimal balance;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime profileCreated;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updated;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.carNumber = user.getCarNumber();
        this.balance = user.getBalance();
        this.profileCreated = user.getCreated();
        this.created = user.getCreated();
        this.updated = user.getUpdated();
    }
}
