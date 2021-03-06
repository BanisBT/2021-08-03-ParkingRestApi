package com.tbarauskas.parkingrestapi.dto.user;

import com.tbarauskas.parkingrestapi.entity.user.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoleResponseDTO {

    private Long id;

    private String name;

    public UserRoleResponseDTO(UserRole role) {
        this.id = role.getId();
        this.name = role.getUserRole();
    }
}
