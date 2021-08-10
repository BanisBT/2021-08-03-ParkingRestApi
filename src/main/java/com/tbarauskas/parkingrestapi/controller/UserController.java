package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.user.CreateUserRequestDTO;
import com.tbarauskas.parkingrestapi.dto.user.CreateUserResponseDTO;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDTO createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        User user = userService.createUser(new User(createUserRequestDTO));
        log.debug("User - {} has been successfully created", user);
        return new CreateUserResponseDTO(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        log.debug("User - {} has been successfully updated", user);
        return user;
    }
}
