package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.user.UserRole;
import com.tbarauskas.parkingrestapi.exceptsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.tbarauskas.parkingrestapi.model.UserRoleName.MANAGER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private UserRoleRepository roleRepository;

    @Mock
    private UserRole role;

    @InjectMocks
    private UserRoleService roleService;

    @Test
    void testGetUserRole() {
        when(roleRepository.getUserRoleByUserRole(MANAGER.name())).thenReturn(Optional.of(role));

        UserRole userRole = roleService.getUserRole(MANAGER.name());

        assertEquals(role, userRole);
    }

    @Test
    void testGetUserRoleThenNotExist() {
        when(roleRepository.getUserRoleByUserRole(any(String.class))).thenReturn(Optional.empty());

        assertThrows(AppParametersInDateBaseNotFoundException.class, () -> roleService.getUserRole("not exist name"));
    }
}
