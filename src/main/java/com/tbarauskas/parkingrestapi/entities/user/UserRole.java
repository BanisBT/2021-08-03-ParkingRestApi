package com.tbarauskas.parkingrestapi.entities.user;

import com.tbarauskas.parkingrestapi.model.UserRoleName;
import lombok.Data;

@Data
public class UserRole {

    private Long id;

    private UserRoleName userRole;
}
