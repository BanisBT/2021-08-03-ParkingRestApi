package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.entity.user.UserRole;
import com.tbarauskas.parkingrestapi.exceptsion.UsernameAlreadyExistException;
import com.tbarauskas.parkingrestapi.repository.UserRepository;
import com.tbarauskas.parkingrestapi.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.tbarauskas.parkingrestapi.model.UserRoleName.REGULAR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void getUser() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setSurname("Testukas");
        user.setPassword("labaiGeras");
        UserRole role = new UserRole();
        role.setUserRole(REGULAR.name());

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(roleService.getUserRole(REGULAR.name())).thenReturn(role);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("Encoded password");
        when(userRepository.save(user)).thenReturn(user);

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        verify(roleService, times(1)).getUserRole(REGULAR.name());
        verify(passwordEncoder, times(1)).encode("labaiGeras");

        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
        assertEquals("Encoded password", user.getPassword());
    }

    @Test
    void testCreateUserWithExistUsername() {
        User user = new User();
        user.setUsername("Username");

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(UsernameAlreadyExistException.class, () -> userService.createUser(user));
        verify(userRepository, times(0)).save(user);
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUsersFines() {
    }

    @Test
    void getUsersUnpaidFines() {
    }

    @Test
    void getUsersTickets() {
    }

    @Test
    void isEnoughMoneyToPayForTicket() {
    }

    @Test
    void isEnoughMoneyToPayForFine() {
    }
}
