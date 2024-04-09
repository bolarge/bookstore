package com.demo.bookstore.inventory;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthorRecord(@NotBlank @NotNull @Email(message = "Valid Email is required!") String email,
                           @NotBlank @NotNull @Size(min = 3, max = 15, message = "Name is required!") String name,
                           @NotBlank @NotNull @Size(min = 20, max = 100, message = "Valid phone number is required!") String bio,
                           Long id,
                           String websiteUrl
                           ) {}
