package com.tbarauskas.parkingrestapi.repositories;

import com.tbarauskas.parkingrestapi.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(Long id);
}
