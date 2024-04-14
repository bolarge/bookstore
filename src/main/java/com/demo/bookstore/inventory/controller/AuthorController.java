package com.demo.bookstore.inventory.controller;

import com.demo.bookstore.inventory.AuthorRequest;
import com.demo.bookstore.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Author Resource")
@RequestMapping("/api/v1")
@RestController
public class AuthorController {
    private final InventoryService inventoryService;
    @PostMapping("/authors")
    public ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorRequest authorRequest){
        var requestResponse = inventoryService.createAuthor(authorRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newAuthorUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requestResponse.getData().id()).toUri();
        responseHeaders.setLocation(newAuthorUri);
        return new ResponseEntity<>(requestResponse, responseHeaders, HttpStatus.CREATED);
    }
    @GetMapping("/authors")
    public ResponseEntity<?> getAllAuthors(){
        return new ResponseEntity<>(inventoryService.fetchAllAuthors(), HttpStatus.OK);
    }
}
