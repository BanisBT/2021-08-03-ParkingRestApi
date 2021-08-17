package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.user.LoginUserResponseDTO;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.service.JwtService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final JwtService jwtService;

    public LoginController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping
    public LoginUserResponseDTO login(@AuthenticationPrincipal User user) {
        return new LoginUserResponseDTO(jwtService.createToken(user));
    }
}
