package com.demo.bookstore.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByName(String authorName);
}
