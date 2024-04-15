package com.demo.bookstore.inventory.controller;

import com.demo.bookstore.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Inventories", description = "Inventory Resource")
@RequestMapping("/api/v1")
@RestController
public class InventoryController {
    private final InventoryService inventoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/inventories")
    public ResponseEntity<?> getAllInventories(){
        return new ResponseEntity<>(inventoryService.fetchAllInventories(), HttpStatus.OK);
    }
}
