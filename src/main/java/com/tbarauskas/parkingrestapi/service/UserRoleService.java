package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.user.UserRole;
import com.tbarauskas.parkingrestapi.exceptsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository roleRepository;

    public UserRoleService(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserRole getUserRole(String roleName) {
        return roleRepository.getUserRoleByUserRole(roleName)
                .orElseThrow(() -> new AppParametersInDateBaseNotFoundException(roleName));
    }
}
