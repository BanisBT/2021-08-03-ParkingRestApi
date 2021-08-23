package com.tbarauskas.parkingrestapi.dto.user;

import com.tbarauskas.parkingrestapi.entity.user.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserResponseDTO {

    private Long id;

    private String username;

    private String name;

    private String surname;

    private String carNumber;

    private BigDecimal balance;

    private Set<UserRoleResponseDTO> roles;

    private LocalDateTime profileCreated;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.carNumber = user.getCarNumber();
        this.balance = user.getBalance();
        this.profileCreated = user.getCreated();
        this.roles = user.getRoles().stream().map(UserRoleResponseDTO::new).collect(Collectors.toSet());
    }
}
