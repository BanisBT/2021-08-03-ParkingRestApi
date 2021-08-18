package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetUserById() {
        User user = userRepository.getUserById(1L).orElse(null);

        assert user != null;
        assertEquals(userRepository.getUserByUsername("Banis").get().getId(), user.getId());
        assertEquals(userRepository.getUserByUsername("Banis").get().getUsername(), user.getUsername());
        assertEquals(1L, user.getId());
    }

    @Test
    void testGetUserByUsername() {
        User user = userRepository.getUserByUsername("Banis").orElse(null);

        assert user != null;
        assertEquals("Banis", user.getUsername());
        assertEquals(1L, user.getId());
    }

    @Test
    void testGetUsersByUsernameOrSurnameContainingIgnoreCase() {
        List<User> users = userRepository.getUsersByUsernameContainingIgnoreCase("ban");

        assertEquals(1L, users.size());
    }
}
