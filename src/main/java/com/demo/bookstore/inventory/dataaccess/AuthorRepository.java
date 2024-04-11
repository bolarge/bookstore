package com.demo.bookstore.inventory.dataaccess;

import com.demo.bookstore.inventory.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByName(String authorName);
}
