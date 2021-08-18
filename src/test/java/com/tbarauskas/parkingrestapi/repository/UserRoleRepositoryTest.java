package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.tbarauskas.parkingrestapi.model.UserRoleName.MANAGER;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository roleRepository;

    @Test
    void testGetUserRoleByUserRole() {
        UserRole role = roleRepository.getUserRoleByUserRole(MANAGER.name()).orElse(null);

        assert role != null;
        assertEquals(2L, role.getId());
        assertEquals(MANAGER.name(), role.getUserRole());
    }
}
