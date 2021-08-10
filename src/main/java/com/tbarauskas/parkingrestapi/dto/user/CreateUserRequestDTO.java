package com.tbarauskas.parkingrestapi.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateUserRequestDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String carNumber;
}
