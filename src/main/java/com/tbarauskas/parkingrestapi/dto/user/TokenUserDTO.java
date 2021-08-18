package com.tbarauskas.parkingrestapi.dto.user;

import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.entity.user.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class TokenUserDTO {

    private Long id;

    private String username;

    private String name;

    private String surname;

    private String carNumber;

    private Set<UserRole> roles;

    public TokenUserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.carNumber = user.getCarNumber();
        this.roles = user.getRoles();
    }
}
