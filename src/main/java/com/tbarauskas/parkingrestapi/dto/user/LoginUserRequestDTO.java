package com.tbarauskas.parkingrestapi.dto.user;

import lombok.Data;

@Data
public class LoginUserRequestDTO {

    private String username;

    private String password;
}
