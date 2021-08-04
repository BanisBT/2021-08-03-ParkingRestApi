package com.tbarauskas.parkingrestapi.controllers;

import com.tbarauskas.parkingrestapi.entities.user.User;
import com.tbarauskas.parkingrestapi.entities.user.UserRole;
import com.tbarauskas.parkingrestapi.model.UserRoleName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final LocalDateTime now = LocalDateTime.now();

    private final UserRole regular = new UserRole(1L, UserRoleName.REGULAR, now, now);

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return new User(id, "Banis", "Geras", "Tomas", "Barauskas",
                "XXX 999", BigDecimal.ZERO, regular, now, now);
    }

    @GetMapping
    public List<User> users () {
        return Collections.singletonList(new User(1L, "Banis", "Geras", "Tomas", "Barauskas",
                "XXX 999", BigDecimal.ZERO, regular, now, now));
    }
}
