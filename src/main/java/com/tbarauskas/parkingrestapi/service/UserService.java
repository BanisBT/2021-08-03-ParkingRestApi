package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
