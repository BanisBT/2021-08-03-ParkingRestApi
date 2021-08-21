package com.tbarauskas.parkingrestapi.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String carNumber;
}
