package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.user.UserRequestDTO;
import com.tbarauskas.parkingrestapi.dto.user.LoginUserResponseDTO;
import com.tbarauskas.parkingrestapi.dto.user.UserResponseDTO;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.service.JwtService;
import com.tbarauskas.parkingrestapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/")
public class MainController {

    private final JwtService jwtService;

    private final UserService userService;

    public MainController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginUserResponseDTO login(@AuthenticationPrincipal User user) {
        return new LoginUserResponseDTO(jwtService.createToken(user), new UserResponseDTO(user));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User user = userService.createUser(new User(userRequestDTO));
        log.debug("User - {} has been successfully created", user);
        return new UserResponseDTO(user);
    }
}
