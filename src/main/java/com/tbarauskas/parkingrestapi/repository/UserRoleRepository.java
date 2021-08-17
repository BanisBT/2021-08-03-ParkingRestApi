package com.tbarauskas.parkingrestapi.repository;

import com.tbarauskas.parkingrestapi.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
