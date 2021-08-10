package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(Long id);
}
