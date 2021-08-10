package com.tbarauskas.parkingrestapi.entity.user;

import com.tbarauskas.parkingrestapi.model.UserRoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserRole {

    private Long id;

    private UserRoleName userRole;

    private LocalDateTime created;

    private LocalDateTime updated;
}
