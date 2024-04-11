package com.demo.bookstore.inventory.controller;

import com.demo.bookstore.inventory.BookRequest;
import com.demo.bookstore.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Books", description = "Book Resource")
@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    private final InventoryService inventoryService;

    @PostMapping("")
    public ResponseEntity<?> stockBookInventory(@Valid @RequestBody BookRequest bookRequest){
        var requestResponse = inventoryService.stockUpBooks(bookRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newBookUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requestResponse.getData().id()).toUri();
        responseHeaders.setLocation(newBookUri);
        return new ResponseEntity<>(requestResponse, responseHeaders, HttpStatus.CREATED);
    }
}
