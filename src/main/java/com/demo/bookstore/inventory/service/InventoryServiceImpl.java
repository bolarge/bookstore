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
    public GenericResponse<AuthorRecord> createAuthor(AuthorRecord authorRecord) {

        var newAuthor = new Author(authorRecord.name(),authorRecord.email(), authorRecord.bio());
        newAuthor = authorRepository.save(newAuthor);
        var requestResponse = new GenericResponse<AuthorRecord>();
        var savedAuthorRecord = new AuthorRecord(newAuthor.getName(), newAuthor.getEmail(),
                newAuthor.getBio(),newAuthor.getId(),newAuthor.getWebsiteUrl());

        requestResponse.setData(savedAuthorRecord);
        requestResponse.setMessage("Author successfully saved");
        return requestResponse;
    }

    @Override
    public GenericResponse<BookRecord> stockUpBooks(BookRecord bookRecord) {
        var bookAuthor = findByAuthorName(bookRecord.author());
        var newBook = new Book(bookRecord.title(), bookRecord.isbn(), bookRecord.publicationYear(),
                BookGenre.valueOf(bookRecord.genre().toUpperCase()), bookAuthor);
        newBook = bookRepository.save(newBook);

        var freshStock = new Inventory(bookRecord.title(), bookRecord.itemDescription(), bookRecord.purchaseOrderId(),
                bookRecord.quantity(), newBook, InventoryStatus.AVAILABLE);
        freshStock = inventoryRepository.save(freshStock);

        var requestResponse = new GenericResponse<BookRecord>();

        var bRecord = new BookRecord(newBook.getTitle(), newBook.getIsbn(), newBook.getPublicationYear().toString(), newBook.getBookGenre().toString(),
                freshStock.getItemQty(), bookRecord.author(), bookRecord.purchaseOrderId(), newBook.getId(), bookRecord.itemDescription());
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
