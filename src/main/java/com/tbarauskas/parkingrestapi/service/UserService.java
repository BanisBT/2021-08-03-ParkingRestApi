package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.getUserById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updateUser) {
        User user = userRepository.getUserById(updateUser.getId());

        if (user != null) {
            updateUser.setCreated(user.getCreated());
            return userRepository.save(updateUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        if (getUser(id) != null) {
            userRepository.deleteById(id);
        }
    }
}
