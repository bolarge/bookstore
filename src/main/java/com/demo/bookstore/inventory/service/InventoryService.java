package com.demo.bookstore.inventory.service;

import com.demo.bookstore.inventory.*;
import com.demo.bookstore.utils.GenericResponse;

public interface InventoryService {
    GenericResponse<AuthorRecord> createAuthor(AuthorRecord authorRecord);
    GenericResponse<BookRecord> stockUpBooks(BookRecord bookRecord);
    Author findByAuthorName(String authorName);
    Book findBookByTitle(String title);
    Book findBookByGenre(BookGenre bookGenre);
    Book findBookByPublicationYear(String year);
    Book findBookByAuthor(Author author);
}
