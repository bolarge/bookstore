package com.demo.bookstore.crm.dataaccess;

import com.demo.bookstore.crm.Identity;
import com.demo.bookstore.crm.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByIdentity(Identity identity);
}
