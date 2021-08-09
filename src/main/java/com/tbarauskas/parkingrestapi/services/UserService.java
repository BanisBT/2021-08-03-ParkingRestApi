package com.tbarauskas.parkingrestapi.services;

import com.tbarauskas.parkingrestapi.entities.user.User;
import com.tbarauskas.parkingrestapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
