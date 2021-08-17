package com.tbarauskas.parkingrestapi.dto.user;

import lombok.Data;

@Data
public class LoginUserResponseDTO {

    private final String loginToken;
}
