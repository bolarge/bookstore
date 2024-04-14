package com.demo.bookstore.crm.controller;

import com.demo.bookstore.crm.datatransfer.UserRecord;
import com.demo.bookstore.crm.service.IdentityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "Users", description = "User Resource")
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final IdentityService identityService;
    @PostMapping("/{userId}")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRecord userRecord, @PathVariable Long userId){
        var userResponse = identityService.createUser(userRecord, userId);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(identityService.fetchAllUsers(), HttpStatus.OK);
    }
}
