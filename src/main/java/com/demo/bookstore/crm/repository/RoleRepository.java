package com.demo.bookstore.crm.repository;

import com.demo.bookstore.crm.Role;
import com.demo.bookstore.crm.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUserRole(UserRole userRole);
}
