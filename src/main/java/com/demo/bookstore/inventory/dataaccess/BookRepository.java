package com.demo.bookstore.inventory.dataaccess;

import com.demo.bookstore.inventory.Author;
import com.demo.bookstore.inventory.Book;
import com.demo.bookstore.inventory.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByTitle(String title);
    Book findBookByBookGenre(BookGenre bookGenre);
    Book findBookByPublicationYear(String year);
    Book findBookByAuthor(Author author);
}
