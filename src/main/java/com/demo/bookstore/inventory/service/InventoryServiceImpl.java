package com.demo.bookstore.inventory.service;

import com.demo.bookstore.inventory.*;
import com.demo.bookstore.inventory.repository.AuthorRepository;
import com.demo.bookstore.inventory.repository.BookRepository;
import com.demo.bookstore.inventory.repository.InventoryRepository;
import com.demo.bookstore.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryServiceImpl implements InventoryService{

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public GenericResponse<AuthorRequest> createAuthor(AuthorRequest authorRequest) {

        var newAuthor = new Author(authorRequest.name(), authorRequest.email(), authorRequest.bio());
        newAuthor = authorRepository.save(newAuthor);
        var requestResponse = new GenericResponse<AuthorRequest>();
        var savedAuthorRecord = new AuthorRequest(newAuthor.getName(), newAuthor.getEmail(),
                newAuthor.getBio(),newAuthor.getId(),newAuthor.getWebsiteUrl());

        requestResponse.setData(savedAuthorRecord);
        requestResponse.setMessage("Author successfully saved");
        return requestResponse;
    }

    @Override
    public GenericResponse<BookRequest> stockUpBooks(BookRequest bookRequest) {
        var bookAuthor = findByAuthorName(bookRequest.author());
        var newBook = new Book(bookRequest.title(), bookRequest.isbn(), bookRequest.publicationYear(),
                BookGenre.valueOf(bookRequest.genre().toUpperCase()), bookAuthor, BigDecimal.valueOf(bookRequest.price()));
        newBook = bookRepository.save(newBook);

        var freshStock = new Inventory(bookRequest.title(), bookRequest.itemDescription(), bookRequest.purchaseOrderId(),
                bookRequest.quantity(), newBook, InventoryStatus.AVAILABLE);
        freshStock = inventoryRepository.save(freshStock);

        var requestResponse = new GenericResponse<BookRequest>();

        var bRecord = new BookRequest(newBook.getTitle(), newBook.getIsbn(), newBook.getPublicationYear().toString(), newBook.getBookGenre().toString(),
                newBook.getPrice().toBigInteger().longValue(), freshStock.getItemQty(), bookRequest.author(), bookRequest.purchaseOrderId(), newBook.getId(), bookRequest.itemDescription());
        requestResponse.setData(bRecord);
        requestResponse.setMessage("Book successfully created");
        return requestResponse;
    }
    @Override
    @Transactional(readOnly = true)
    public Author findByAuthorName(String authorName) {
        return authorRepository.findAuthorByName(authorName);
    }

    @Override
    @Transactional(readOnly = true)
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByPublicationYear(String publicationYear) {
        return bookRepository.findByPublicationYear(publicationYear);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByGenre(BookGenre bookGenre) {
        return bookRepository.findByBookGenre(bookGenre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksUsingAuthorName(String authorName) {
        var foundAuthor = findByAuthorName(authorName);
        return bookRepository.findByAuthor(foundAuthor);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<Author> fetchAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Book> fetchAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Inventory> fetchAllInventories() {
        return inventoryRepository.findAll();
    }
}
