package com.demo.bookstore.inventory.repository;

import com.demo.bookstore.inventory.Author;
import com.demo.bookstore.inventory.Book;
import com.demo.bookstore.inventory.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
    List<Book> findByPublicationYear(String publicationYear);
    List<Book> findByBookGenre(BookGenre bookGenre);
    List<Book> findByAuthor(Author author);
}
