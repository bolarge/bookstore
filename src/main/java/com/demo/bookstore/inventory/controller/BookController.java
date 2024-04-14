package com.demo.bookstore.inventory.controller;

import com.demo.bookstore.inventory.BookGenre;
import com.demo.bookstore.inventory.BookRequest;
import com.demo.bookstore.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Books", description = "Book Resource")
@RequestMapping("/api/v1")
@RestController
public class BookController {

    private final InventoryService inventoryService;
    @PostMapping("/books")
    public ResponseEntity<?> stockBookIntoInventory(@Valid @RequestBody BookRequest bookRequest){
        var requestResponse = inventoryService.stockUpBooks(bookRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newBookUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requestResponse.getData().id()).toUri();
        responseHeaders.setLocation(newBookUri);
        return new ResponseEntity<>(requestResponse, responseHeaders, HttpStatus.CREATED);
    }
    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks(){
        return new ResponseEntity<>(inventoryService.fetchAllBooks(), HttpStatus.OK);
    }
    @GetMapping("/books/search-by-title/{title}")
    public ResponseEntity<?> findABookByTitle(@PathVariable("title") String title ){
        return new ResponseEntity<>(inventoryService.findByTitle(title), HttpStatus.OK);
    }
    @GetMapping("/books/search-by-year/{publicationYear}")
    public ResponseEntity<?> findBookByPublicationYear(@PathVariable("publicationYear") String publicationYear ){
        return new ResponseEntity<>(inventoryService.findByPublicationYear(publicationYear), HttpStatus.OK);
    }
    @GetMapping("/books/search-by-genre/{genre}")
    public ResponseEntity<?> findBookByGenre(@PathVariable("genre") String genre ){
        return new ResponseEntity<>(inventoryService.findByGenre(BookGenre.valueOf(genre.toUpperCase())), HttpStatus.OK);
    }
    @GetMapping("/books/search-by-author-name/{authorName}")
    public ResponseEntity<?> findBookByAuthor(@PathVariable("authorName") String authorName ){
        return new ResponseEntity<>(inventoryService.getBooksUsingAuthorName(authorName), HttpStatus.OK);
    }
}
