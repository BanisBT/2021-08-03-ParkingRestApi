package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineResponseDTO;
import com.tbarauskas.parkingrestapi.dto.user.CreateUserRequestDTO;
import com.tbarauskas.parkingrestapi.dto.user.UserResponseDTO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.service.UserService;
import com.tbarauskas.parkingrestapi.service.parking.ParkingFineService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Api(tags = "User controller")
public class UserController {

    private final UserService userService;

    private final ParkingFineService fineService;

    public UserController(UserService userService, ParkingFineService fineService) {
        this.userService = userService;
        this.fineService = fineService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(required = false, value = "search") String search) {
        return userService.getUsers(search);
    }

    @GetMapping("/{id}/tickets")
    public List<ParkingTicket> getUsersTickets(@PathVariable Long id) {
        return userService.getUsersTickets(id);
    }

    @GetMapping("/{id}/fines")
    public List<ParkingFine> getUsersFines(@PathVariable Long id) {
        return userService.getUsersFines(id);
    }

    @GetMapping("/{id}/fines/unpaid")
    public List<ParkingFine> getUsersUnpaidFines(@PathVariable Long id) {
        return userService.getUsersUnpaidFines(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        User user = userService.updateUser(id, new User(createUserRequestDTO));
        log.debug("User - {} has been successfully updated", user);
        return new UserResponseDTO(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        log.debug("User - {} has been successfully deleted", userService.getUser(id));
        userService.deleteUser(id);
    }
}
