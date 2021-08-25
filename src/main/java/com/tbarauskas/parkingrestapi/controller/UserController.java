package com.tbarauskas.parkingrestapi.controller;

import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineResponseDTO;
import com.tbarauskas.parkingrestapi.dto.parking.ticket.ParkingTicketResponseDTO;
import com.tbarauskas.parkingrestapi.dto.user.UserRequestDTO;
import com.tbarauskas.parkingrestapi.dto.user.UserResponseDTO;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.service.UserService;
import com.tbarauskas.parkingrestapi.service.parking.ParkingTicketService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users")
@Api(tags = "User controller")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') || principal.id==#id")
    public UserResponseDTO getUser(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return new UserResponseDTO(userService.getUser(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public List<UserResponseDTO> getUsers(@RequestParam(required = false, value = "search") String search) {
        return userService.getUsers(search).stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/tickets")
    public List<ParkingTicketResponseDTO> getUsersTickets(@PathVariable Long id) {
        return userService.getUsersTickets(id).stream()
                .map(ParkingTicketResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/fines")
    public List<ParkingFineResponseDTO> getUsersFines(@PathVariable Long id) {
        return userService.getUsersFines(id).stream()
                .map(ParkingFineResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/fines/unpaid")
    public List<ParkingFineResponseDTO> getUsersUnpaidFines(@PathVariable Long id) {
        return userService.getUsersUnpaidFines(id).stream()
                .map(ParkingFineResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        User user = userService.updateUser(id, new User(userRequestDTO));
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
