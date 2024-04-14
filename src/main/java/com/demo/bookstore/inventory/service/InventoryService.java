package com.demo.bookstore.inventory.service;

import com.demo.bookstore.inventory.*;
import com.demo.bookstore.utils.GenericResponse;

import java.util.List;

public interface InventoryService {
    //Authors
    GenericResponse<AuthorRequest> createAuthor(AuthorRequest authorRequest);
    Iterable<Author> fetchAllAuthors();
    Author findByAuthorName(String authorName);

    //Books
    Book findByTitle(String title);
    List<Book> findByPublicationYear(String year);
    List<Book>  findByGenre(BookGenre bookGenre);
    List<Book> getBooksUsingAuthorName(String authorName);
    Iterable<Book> fetchAllBooks();

    //Inventories
    GenericResponse<BookRequest> stockUpBooks(BookRequest bookRequest);
    Iterable<Inventory> fetchAllInventories();
}
