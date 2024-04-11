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
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final IdentityService identityService;
    @PostMapping("/{userIdentity}")
    public ResponseEntity<UserRecord> registerUser(@Valid @RequestBody UserRecord userRecord, @PathVariable Long userIdentity){
        UserRecord newRecord = identityService.createUserProfile(userRecord, userIdentity);
        return new ResponseEntity<>(newRecord, HttpStatus.CREATED);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(identityService.fetchAllUsers(), HttpStatus.OK);
    }
}
