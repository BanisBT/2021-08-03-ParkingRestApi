package com.tbarauskas.parkingrestapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponseDTO {

    private String loginToken;

    private UserResponseDTO userDTO;
}
