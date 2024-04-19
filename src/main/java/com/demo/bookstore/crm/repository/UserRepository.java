package com.demo.bookstore.crm.repository;

import com.demo.bookstore.crm.Identity;
import com.demo.bookstore.crm.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByIdentity(Identity identity);
    @Query(value = "SELECT u FROM User u WHERE u.lastName LIKE ?1")
    List<User> findUsersWithLastNameLike(String lastName);
}
