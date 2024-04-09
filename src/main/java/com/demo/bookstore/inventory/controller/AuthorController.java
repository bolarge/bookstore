package com.demo.bookstore.inventory.controller;

import com.demo.bookstore.inventory.AuthorRecord;
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
@Tag(name = "Author", description = "Author Resource")
@RequestMapping("v1/authors")
@RestController
public class AuthorController {

    private final InventoryService inventoryService;

    @PostMapping("")
    public ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorRecord authorRecord){
        var requestResponse = inventoryService.createAuthor(authorRecord);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newAuthorUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requestResponse.getData().id()).toUri();
        responseHeaders.setLocation(newAuthorUri);
        return new ResponseEntity<>(requestResponse, responseHeaders, HttpStatus.CREATED);
    }

  /*  @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable("id") Long id){
        var queryResponse = b2BService.getByEntityId(id);
        return ResponseEntity.ok(queryResponse);
    }*/

}
