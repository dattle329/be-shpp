package com.own.moviebooking2.repository;

import com.own.moviebooking2.entity.UserRole;
import com.own.moviebooking2.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRole(Role role);
}
