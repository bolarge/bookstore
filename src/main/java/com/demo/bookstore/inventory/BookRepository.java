package com.demo.bookstore.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByTitle(String title);
    Book findBookByBookGenre(BookGenre bookGenre);
    Book findBookByPublicationYear(String year);
    Book findBookByAuthor(Author author);
}
