package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.exceptsion.AppParametersInDateBaseNotFoundException;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.exceptsion.UsernameAlreadyExistException;
import com.tbarauskas.parkingrestapi.model.UserRoleName;
import com.tbarauskas.parkingrestapi.repository.UserRepository;
import com.tbarauskas.parkingrestapi.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, UserRoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public User getUser(Long id) {
        return userRepository.getUserById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<User> getUsers(String search) {
        if (search == null) {
            return userRepository.findAll();
        }
        return userRepository.getUsersByUsernameOrSurnameContainingIgnoreCase(search, search);
    }

    public User createUser(User user) {
        if (userRepository.getUserByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException(user.getUsername());
        }
        user.setRoles(Set.of(roleRepository.getUserRoleByUserRole(UserRoleName.REGULAR.name()).
                orElseThrow(() -> new AppParametersInDateBaseNotFoundException(UserRoleName.REGULAR.name()))));
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User updateUser(Long id, User updateUser) {
        User user = getUser(updateUser.getId());
        updateUser.setCreated(user.getCreated());
        return userRepository.save(updateUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(getUser(id).getId());
    }

    public List<ParkingFine> getUsersFines(Long id) {
        return getUser(id).getFines();
    }

    public List<ParkingTicket> getUsersTickets(Long id) {
        return getUser(id).getTickets();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
