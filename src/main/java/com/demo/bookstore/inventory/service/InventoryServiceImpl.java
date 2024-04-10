package com.demo.bookstore.inventory.service;

import com.demo.bookstore.inventory.*;
import com.demo.bookstore.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                BookGenre.valueOf(bookRequest.genre().toUpperCase()), bookAuthor);
        newBook = bookRepository.save(newBook);

        var freshStock = new Inventory(bookRequest.title(), bookRequest.itemDescription(), bookRequest.purchaseOrderId(),
                bookRequest.quantity(), newBook, InventoryStatus.AVAILABLE);
        freshStock = inventoryRepository.save(freshStock);

        var requestResponse = new GenericResponse<BookRequest>();

        var bRecord = new BookRequest(newBook.getTitle(), newBook.getIsbn(), newBook.getPublicationYear().toString(), newBook.getBookGenre().toString(),
                freshStock.getItemQty(), bookRequest.author(), bookRequest.purchaseOrderId(), newBook.getId(), bookRequest.itemDescription());
        requestResponse.setData(bRecord);
        requestResponse.setMessage("Book successfully created");
        return requestResponse;
    }

    @Override
    public Author findByAuthorName(String authorName) {
        return authorRepository.findAuthorByName(authorName);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title);
    }

    @Override
    public Book findBookByGenre(BookGenre bookGenre) {
        return bookRepository.findBookByBookGenre(bookGenre);
    }

    @Override
    public Book findBookByPublicationYear(String year) {
        return bookRepository.findBookByPublicationYear(year);
    }

    @Override
    public Book findBookByAuthor(Author author) {
        return bookRepository.findBookByAuthor(author);
    }


}
